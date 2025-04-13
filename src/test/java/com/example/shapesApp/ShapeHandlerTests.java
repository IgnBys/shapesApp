package com.example.shapesApp;

import com.example.shapesApp.handler.RectangleHandler;
import com.example.shapesApp.handler.ShapeHandler;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShapeHandlerTests {
    @Test
    void shouldThrowValidationExceptionWhenParameterAMissing() {
        ShapeHandler shapeHandler = new RectangleHandler();
        Map<String, Object> params = new HashMap<>();
        ValidationException exception = assertThrows(ValidationException.class, () ->
                shapeHandler.validateParameters(params));
        assertEquals("Rectangle requires numeric parameter 'a'", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenParameterANotNumber() {
        ShapeHandler shapeHandler = new RectangleHandler();
        Map<String, Object> params = Map.of("a", "10");
        ValidationException exception = assertThrows(ValidationException.class, () ->
                shapeHandler.validateParameters(params));
        assertEquals("Rectangle requires numeric parameter 'a'", exception.getMessage());
    }

    @Test
    void shouldPassValidationWhenParameterAIsValidNumber() {
        ShapeHandler shapeHandler = new RectangleHandler();
        Map<String, Object> params = Map.of("a", 10);
        assertDoesNotThrow(() -> shapeHandler.validateParameters(params));
    }
}
