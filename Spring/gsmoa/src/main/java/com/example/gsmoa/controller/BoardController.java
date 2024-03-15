package com.example.gsmoa.controller;

import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.repository.BoardRepository;
import com.example.gsmoa.repository.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
class BoardController {

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    List<BoardEntity> all(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title, content);
        }
    }
    // end::get-aggregate-root[]

    @PostMapping("/boards")
    BoardEntity newBoard(@RequestBody BoardEntity newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{post_id}")
    BoardEntity one(@PathVariable Long post_id) {
        return repository.findById(post_id).map(board -> {
            // Hibernate.initialize 메서드를 사용하여 Lazy 로딩을 강제로 초기화할 수 있습니다.
            // 이 방법은 성능에 영향을 줄 수 있으므로, 필요한 경우에만 사용하세요.
            // Hibernate.initialize(board.getComments());

            // 위의 Hibernate.initialize 대신, 아래와 같이 게시글의 댓글에 접근하는 것만으로도
            // LAZY 로딩이 수행되어 댓글 정보를 가져올 수 있습니다.
            board.getComments().size();

            return board;
        }).orElseThrow(() -> new BoardNotFoundException(post_id));
    }



    @PutMapping("/boards/{post_id}")
    BoardEntity replaceBoard(@RequestBody BoardEntity newBoard, @PathVariable Long id) {
        return repository.findById(id)
                .map(Board -> {
                    Board.setTitle(newBoard.getTitle());
                    Board.setContent(newBoard.getContent());
                    return repository.save(Board);
                })
                .orElseGet(() -> {
                    newBoard.setPost_id(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{post_id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}