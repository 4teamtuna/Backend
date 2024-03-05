package com.example.gsmoa.controller;

import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.repository.BoardRepository;
import com.example.gsmoa.service.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    List<BoardEntity> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/boards")
    BoardEntity newBoard(@RequestBody BoardEntity newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}")
    BoardEntity one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
    }

    @PutMapping("/boards/{id}")
    BoardEntity replaceBoard(@RequestBody BoardEntity newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(Board -> {
                    Board.setBoardTitle(newBoard.getBoardTitle());
                    Board.setBoardContents(newBoard.getBoardContents());
                    return repository.save(Board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}