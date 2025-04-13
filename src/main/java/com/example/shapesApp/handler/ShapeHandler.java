package com.example.shapesApp.handler;

import java.util.Map;

public interface ShapeHandler {
    String getType();

    void validateParameters(Map<String, Object> parameters);

}
