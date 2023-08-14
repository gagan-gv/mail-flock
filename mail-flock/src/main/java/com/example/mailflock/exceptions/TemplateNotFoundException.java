package com.example.mailflock.exceptions;

public class TemplateNotFoundException extends Exception{
    public TemplateNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
