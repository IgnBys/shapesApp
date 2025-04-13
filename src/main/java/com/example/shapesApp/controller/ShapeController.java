package com.example.shapesApp.controller;

import com.example.shapesApp.model.ShapeRequest;
import com.example.shapesApp.model.ShapeResponse;
import com.example.shapesApp.service.ShapeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shapes")
public class ShapeController {
    private final ShapeService shapeService;

    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @PostMapping
    public ResponseEntity<ShapeResponse> addShape(@RequestBody @Valid ShapeRequest shapeRequest){
        ShapeResponse shapeResponse = shapeService.addShape(shapeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(shapeResponse);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<ShapeResponse>> getShapesByType(@PathVariable String type){
        List<ShapeResponse> shapeResponseList = shapeService.getShapesByType(type);
        return ResponseEntity.ok(shapeResponseList);
    }
}
