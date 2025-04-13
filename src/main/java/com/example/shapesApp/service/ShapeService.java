package com.example.shapesApp.service;

import com.example.shapesApp.model.ShapeRequest;
import com.example.shapesApp.exception.ShapeTypeNotSupportedException;
import com.example.shapesApp.handler.ShapeHandler;
import com.example.shapesApp.model.ShapeEntity;
import com.example.shapesApp.model.ShapeResponse;
import com.example.shapesApp.repository.ShapeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShapeService {
    private final ShapeRepository shapeRepository;
    private final Map<String, ShapeHandler> shapeHandlerMap;
    private final ObjectMapper objectMapper;

    public ShapeService(List<ShapeHandler> handlers, ShapeRepository repository,ObjectMapper objectMapper) {
        this.shapeRepository = repository;
        this.objectMapper = objectMapper;
        this.shapeHandlerMap = handlers.stream()
                .collect(Collectors.toMap(ShapeHandler::getType, h -> h));
    }

    public List<ShapeResponse> getShapesByType(String type){
        List<ShapeEntity> shapeEntityList =  shapeRepository.findByType(type);
        return shapeEntityList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    public ShapeResponse addShape(ShapeRequest shapeRequest){
        String type = shapeRequest.getType();
        ShapeHandler handler = shapeHandlerMap.get(type);
        if (handler == null) {
            throw new ShapeTypeNotSupportedException(type);
        }
        handler.validateParameters(shapeRequest.getParameters());
        ShapeEntity shapeEntity = new ShapeEntity();
        shapeEntity.setType(type);
        try {
            String jsonParams = objectMapper.writeValueAsString(shapeRequest.getParameters());
            shapeEntity.setParameters(jsonParams);
        } catch (Exception e) {
            throw new RuntimeException("Error converting parameters to JSON", e);
        }
        shapeRepository.save(shapeEntity);
        return toResponse(shapeEntity);
    }

    public ShapeResponse toResponse(ShapeEntity shapeEntity) {
        Map<String, Object> params;
        try {
            params = objectMapper.readValue(shapeEntity.getParameters(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing parameters", e);
        }

        return new ShapeResponse(shapeEntity.getId(), shapeEntity.getType(), params);
    }

}
