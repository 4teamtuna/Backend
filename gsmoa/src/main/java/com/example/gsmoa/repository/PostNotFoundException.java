package com.example.gsmoa.repository;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Could not find board " + id);
    }
}