package com.example.shapesApp;

import com.example.shapesApp.exception.ShapeTypeNotSupportedException;
import com.example.shapesApp.handler.ShapeHandler;
import com.example.shapesApp.model.ShapeEntity;
import com.example.shapesApp.model.ShapeRequest;
import com.example.shapesApp.model.ShapeResponse;
import com.example.shapesApp.repository.ShapeRepository;
import com.example.shapesApp.service.ShapeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShapeServiceTests {

    private ShapeRepository shapeRepository;
    private ShapeHandler circleHandler;
    private ShapeService shapeService;

    @BeforeEach
    void setup() {
        shapeRepository = mock(ShapeRepository.class);
        ObjectMapper objectMapper = new ObjectMapper();
        circleHandler = mock(ShapeHandler.class);
        when(circleHandler.getType()).thenReturn("circle");
        shapeService = new ShapeService(List.of(circleHandler), shapeRepository, objectMapper);
    }

    @Test
    void shouldAddShapeSuccessfully() {
        ShapeRequest request = new ShapeRequest("circle", Map.of("a", 5));
        doNothing().when(circleHandler).validateParameters(anyMap());
        ShapeResponse response = shapeService.addShape(request);
        assertEquals("circle", response.getType());
        assertEquals(5, response.getParameters().get("a"));
        verify(shapeRepository).save(any(ShapeEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenUnsupportedShapeTypeProvided() {
        ShapeRequest request = new ShapeRequest("triangle", Map.of("a", 3, "b", 4, "c", 5));
        Exception exception = assertThrows(ShapeTypeNotSupportedException.class, () ->
                shapeService.addShape(request));
        assertTrue(exception.getMessage().contains("triangle"));
    }

    @Test
    void shouldReturnListOfShapesByType() {
        ShapeEntity entity = new ShapeEntity();
        entity.setId(1L);
        entity.setType("circle");
        entity.setParameters("{\"a\":10}");
        when(shapeRepository.findByType("circle")).thenReturn(List.of(entity));
        List<ShapeResponse> result = shapeService.getShapesByType("circle");
        assertEquals(1, result.size());
        assertEquals("circle", result.get(0).getType());
        assertEquals(10, result.get(0).getParameters().get("a"));
    }

}
