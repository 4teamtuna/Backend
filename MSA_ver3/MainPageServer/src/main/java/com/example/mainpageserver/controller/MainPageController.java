package com.example.mainpageserver.controller;

import com.example.mainpageserver.entity.DetailsEntity;
import com.example.mainpageserver.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MainPageController {

    private final DetailsRepository detailsRepository;

    @Autowired
    public MainPageController(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @GetMapping("/images/interest")
    public List<DetailsEntity> getImagesByUserInterest(@RequestHeader("User-Token") String token) {
        RestTemplate restTemplate = new RestTemplate();
        String userInterest = restTemplate.getForObject("http://user-server/user/interest?token=" + token, String.class);

        List<DetailsEntity> details = detailsRepository.findByTagsContaining(userInterest);
        return details;
    }
}