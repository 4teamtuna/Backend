package com.example.contest.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HttpDataexchangeController {
    @GetMapping("/http-communication")
    public String getRemoteData() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://example.com"; // Replace with your URL
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }
}
