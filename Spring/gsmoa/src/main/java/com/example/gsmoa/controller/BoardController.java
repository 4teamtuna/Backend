package com.example.gsmoa.controller;

//import com.example.gsmoa.dto.BoardDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
//    private final BoardService boardService;
    @GetMapping("/main")
    public String boardMain() {
        return "boardMain";
    }

    @GetMapping("/write")
    public String writeForm() {
        return "boardWrite";
    }

//    @PostMapping("/write")
//    public String save(@ModelAttribute BoardDTO boardDTO) {
//        boardService.save(boardDTO);
//        return "boardMain";
//    }

}
