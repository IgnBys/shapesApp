package com.example.shapesApp;

import com.example.shapesApp.model.ShapeRequest;
import com.example.shapesApp.model.ShapeResponse;
import com.example.shapesApp.repository.ShapeRepository;
import com.example.shapesApp.service.ShapeService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class ShapeIntegrationTests {

    @Autowired
    private ShapeService shapeService;

    @Autowired
    private ShapeRepository shapeRepository;

    @BeforeEach
    void cleanDatabase() {
        shapeRepository.deleteAll();
    }

    @Test
    void shouldAddAndGetCircleSuccessfully() {
        ShapeRequest request = new ShapeRequest("circle", Map.of("a", 7));
        ShapeResponse saved = shapeService.addShape(request);
        assertNotNull(saved.getId());
        assertEquals("circle", saved.getType());
        assertEquals(7, saved.getParameters().get("a"));
        List<ShapeResponse> shapes = shapeService.getShapesByType("circle");
        assertEquals(1, shapes.size());
    }

    @Test
    void shouldAddRectangleSuccessfully() {
        ShapeRequest request = new ShapeRequest("rectangle", Map.of("a", 5));
        ShapeResponse saved = shapeService.addShape(request);
        assertNotNull(saved.getId());
        assertEquals("rectangle", saved.getType());
        assertEquals(5, saved.getParameters().get("a"));
        List<ShapeResponse> shapes = shapeService.getShapesByType("rectangle");
        assertEquals(1, shapes.size());
    }
    @Test
    void shouldThrowExceptionForUnsupportedShapeType() {
        ShapeRequest request = new ShapeRequest("triangle", Map.of("a", 3, "b", 4, "c", 5));
        Exception exception = assertThrows(RuntimeException.class, () ->
                shapeService.addShape(request));
        assertTrue(exception.getMessage().toLowerCase().contains("triangle"));
    }

    @Test
    void shouldThrowValidationExceptionWhenRectangleParameterIsMissing() {
        ShapeRequest request = new ShapeRequest("rectangle", Map.of());
        Exception exception = assertThrows(ValidationException.class, () ->
                shapeService.addShape(request));
        assertTrue(exception.getMessage().contains("parameter 'a'"));
    }

    @Test
    void shouldReturnEmptyListIfNoShapesOfThatTypeExist() {
        List<ShapeResponse> shapes = shapeService.getShapesByType("circle");
        assertTrue(shapes.isEmpty());
    }
}
