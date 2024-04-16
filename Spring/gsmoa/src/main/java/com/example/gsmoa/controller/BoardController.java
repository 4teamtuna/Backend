package com.example.gsmoa.controller;

import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.entity.User;
import com.example.gsmoa.repository.BoardRepository;
import com.example.gsmoa.repository.BoardNotFoundException;
import com.example.gsmoa.service.BoardService;
import com.example.gsmoa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
class BoardController {

    @Autowired
    private BoardRepository repository; // BoardRepository 인터페이스를 사용하여 DB에 접근합니다.

    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;

    @GetMapping("/boards") // GET 요청을 받아 게시글 목록을 반환합니다.
    // @RequestParam 어노테이션을 사용하여 요청 파라미터를 받아올 수 있습니다.
    // required 속성을 사용하여 해당 파라미터가 필수인지 여부를 지정할 수 있습니다.
    // defaultValue 속성을 사용하여 해당 파라미터가 없을 경우 기본값을 지정할 수 있습니다.
    // title, content 파라미터가 모두 없을 경우, 전체 게시글 목록을 반환합니다.
    // title, content 파라미터 중 하나라도 있을 경우, 해당 파라미터로 검색하여 게시글 목록을 반환합니다.
    // title, content 파라미터가 모두 있을 경우, title 또는 content로 검색하여 게시글 목록을 반환합니다.
    List<BoardEntity> all(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title, content);
        }
    }

    // 게시글 생성
    @PostMapping("/boards")
    public BoardEntity createBoard(@RequestBody BoardEntity newBoard) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null)
            throw new RuntimeException("Current user is not available");
        newBoard.setWriter_id(currentUser.getNickname()); // writer_id에 현재 로그인된 사용자의 이메일을 설정
        return boardService.createPost(newBoard.getTitle(), newBoard.getContent());
    }



    // 게시글 조회
    @GetMapping("/boards/{post_id}")
    // @PathVariable 어노테이션을 사용하여 URL 경로에 있는 post_id 값을 받아올 수 있습니다.
    // 해당 post_id에 해당하는 게시글을 반환합니다.
    // 게시글이 없을 경우, BoardNotFoundException 예외를 발생시킵니다.
    BoardEntity one(@PathVariable Long post_id) {
        return repository.findById(post_id).map(board -> {
            board.setHits(board.getHits() + 1); // 조회수 증가
            board.getComments().size(); // 댓글 목록 조회

            return board; // 조회된 게시글 반환
        }).orElseThrow(() -> new BoardNotFoundException(post_id)); // 게시글이 없을 경우 예외 발생
    }


    // 게시글 수정
    // @PutMapping 어노테이션을 사용하여 PUT 요청을 받아 게시글을 수정합니다.
    // @RequestBody 어노테이션을 사용하여 요청 바디에 있는 JSON 데이터를 BoardEntity 객체로 변환합니다.
    // 해당 post_id에 해당하는 게시글을 찾아 제목과 내용을 수정합니다.
    // 게시글이 없을 경우, 새로운 게시글을 생성합니다.
    // 수정된 게시글을 반환합니다.
    @PutMapping("/boards/{post_id}")
    BoardEntity replaceBoard(@RequestBody BoardEntity newBoard, @PathVariable Long post_id) {
        return repository.findById(post_id)
                .map(Board -> {
                    User currentUser = userService.getCurrentUser();
                    if (currentUser == null || !currentUser.getNickname().equals(Board.getWriter_id())) {
                        throw new RuntimeException("You are not authorized to edit this post");
                    }
                    Board.setTitle(newBoard.getTitle());
                    Board.setContent(newBoard.getContent());
                    return repository.save(Board);
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // 게시글 삭제
    // @DeleteMapping 어노테이션을 사용하여 DELETE 요청을 받아 게시글을 삭제합니다.
    // 해당 post_id에 해당하는 게시글을 삭제합니다.
    // 삭제된 게시글의 post_id를 반환합니다.
    // 게시글이 없을 경우, BoardNotFoundException 예외를 발생시킵니다.
    // 게시글이 삭제되었을 경우, 삭제된 게시글의 post_id를 반환합니다.
    @DeleteMapping("/boards/{post_id}")
    void deleteBoard(@PathVariable Long post_id) {
        BoardEntity Board = repository.findById(post_id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User currentUser = userService.getCurrentUser();
        if (currentUser == null || !currentUser.getNickname().equals(Board.getWriter_id())) {
            throw new RuntimeException("You are not authorized to delete this post");
        }
        repository.deleteById(post_id);
    }
}