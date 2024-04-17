package com.example.gsmoa.service;

import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.entity.User;
import com.example.gsmoa.repository.BoardRepository;
import com.example.gsmoa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public BoardEntity createPost(String title, String content) {
        // 현재 로그인된 사용자의 nickname 가져오기
        User currentUser = userService.getCurrentUser();
        String writerId = currentUser.getNickname();

        BoardEntity newPost = new BoardEntity();
        newPost.setTitle(title); // title 설정
        newPost.setContent(content); // content 설정
        newPost.setWriter_id(writerId); // writer_id에 nickname 설정

        return boardRepository.save(newPost);
    }
}