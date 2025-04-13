package com.example.shapesApp.handler;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RectangleHandler implements ShapeHandler{
    @Override
    public String getType() {
        return "rectangle";
    }
    @Override
    public void validateParameters(Map<String, Object> parameters) {
        if (!parameters.containsKey("a") || !(parameters.get("a") instanceof Number)) {
            throw new ValidationException("Rectangle requires numeric parameter 'a'");
        }
    }
}
