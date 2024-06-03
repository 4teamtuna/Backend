package com.example.authserver.controller;

import com.example.authserver.dto.JoinDto;
import com.example.authserver.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDto joinDTO) {
        joinService.joinProcess(joinDTO);

        return "ok";
    }

}
