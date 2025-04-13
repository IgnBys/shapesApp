package com.example.shapesApp.exception;

public class ShapeTypeNotSupportedException extends RuntimeException{
    public ShapeTypeNotSupportedException(String type){
        super(type + " shape type is not supported");
    }
}
