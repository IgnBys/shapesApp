package com.example.shapesApp.repository;

import com.example.shapesApp.model.ShapeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShapeRepository extends JpaRepository<ShapeEntity, Long> {
    List<ShapeEntity> findByType(String type);
}
