package com.example.gsmoa.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins="*",allowedHeaders = "*")
@Controller
public class HomeController {
    @GetMapping("/")
    public String main(Model model) {
        return "index";
    }

}
