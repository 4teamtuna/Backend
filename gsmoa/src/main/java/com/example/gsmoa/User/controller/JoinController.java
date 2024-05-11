package com.example.gsmoa.User.controller;

import com.example.gsmoa.User.dto.JoinDto;
import com.example.gsmoa.User.service.JoinService;
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
