package com.example.gsmoa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/main")
    public String boardMain() {
        return "boardMain";
    }

    @GetMapping("/write")
    public String writeForm() {
        return "boardWrite";
    }

}
