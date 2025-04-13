package com.example.shapesApp.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ShapeRequest {
    @NotBlank
    private String type;
    @NotNull
    @Valid
    private Map<String,Object> parameters;

    public ShapeRequest(String type, Map<String, Object> parameters) {
        this.type = type;
        this.parameters = parameters;
    }
}
