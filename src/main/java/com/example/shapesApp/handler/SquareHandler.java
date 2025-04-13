package com.example.shapesApp.handler;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SquareHandler implements ShapeHandler {
    @Override
    public String getType() {
        return "square";
    }

    @Override
    public void validateParameters(Map<String, Object> parameters) {
        if (!parameters.containsKey("a") || !(parameters.get("a") instanceof Number)) {
            throw new ValidationException("Square requires numeric parameter 'a'");
        }
    }
}
