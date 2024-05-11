package com.example.community.repository;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Could not find board " + id);
    }
}