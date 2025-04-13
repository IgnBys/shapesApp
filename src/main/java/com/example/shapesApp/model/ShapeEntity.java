package com.example.shapesApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShapeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String parameters;

    public ShapeEntity(String type, String parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    public ShapeEntity() {

    }
}
