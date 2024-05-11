package com.example.mainpageserver.controller;

import com.example.mainpageserver.entity.DetailsEntity;
import com.example.mainpageserver.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {

    private final DetailsRepository detailsRepository;

    @Autowired
    public MainPageController(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<DetailsEntity> getDetails(@PathVariable Long id) {
        return detailsRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // getImage method...
}