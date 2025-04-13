package com.example.shapesApp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ShapeResponse {
    private Long id;
    private String type;
    private Map<String, Object> parameters;

    public ShapeResponse(Long id, String type, Map<String, Object> parameters) {
        this.id = id;
        this.type = type;
        this.parameters = parameters;
    }
}
