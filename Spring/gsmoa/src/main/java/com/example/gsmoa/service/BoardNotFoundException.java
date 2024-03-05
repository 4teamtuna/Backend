package com.example.gsmoa.service;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(Long id) {
        super("Could not find board " + id);
    }
}
