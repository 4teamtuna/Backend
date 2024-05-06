package com.example.gsmoa.controller;

import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public BoardEntity createBoard(@RequestBody BoardEntity board) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        board.setWriterId(username);
        return boardService.createBoard(board);
    }

    @GetMapping
    public List<BoardEntity> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public BoardEntity getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/{id}")
    public BoardEntity updateBoard(@PathVariable Long id, @RequestBody BoardEntity board) {
        return boardService.updateBoard(id, board);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }
}