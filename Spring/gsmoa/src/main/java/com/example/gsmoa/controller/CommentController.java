package com.example.gsmoa.controller;

import com.example.gsmoa.entity.CommentEntity;
import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.entity.User;
import com.example.gsmoa.repository.CommentRepository;
import com.example.gsmoa.repository.BoardRepository;
import com.example.gsmoa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository; // CommentRepository 인터페이스를 사용하여 DB에 접근합니다.

    @Autowired
    private BoardRepository postRepository; // BoardRepository 인터페이스를 사용하여 DB에 접근합니다.

    @Autowired
    private UserService userService;

    // 특정 게시글에 댓글 작성
    // @RequestBody 어노테이션을 사용하여 요청 바디에 있는 JSON 데이터를 CommentEntity 객체로 변환합니다.
    // 해당 post_id에 해당하는 게시글을 찾아 댓글을 추가합니다.
    // 댓글을 저장하고 반환합니다.
    // 게시글이 없을 경우, RuntimeException 예외를 발생시킵니다.
    // 게시글이 있을 경우, 댓글을 저장하고 반환합니다.
    @PostMapping("/boards/{post_id}/comment")
    public CommentEntity addCommentToPost(@PathVariable Long post_id, @RequestBody CommentEntity comment) {
        BoardEntity post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("Post not found"));
        User currentUser = userService.getCurrentUser();
        // 현재 로그인된 사용자가 없을 경우, RuntimeException 예외를 발생시킵니다.
        if (currentUser == null)
            throw new RuntimeException("Current user is not available");
        comment.setWriter(currentUser.getNickname()); // writer_id에 현재 로그인된 사용자의 이메일을 설정
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    // 특정 댓글 수정
    // @RequestBody 어노테이션을 사용하여 요청 바디에 있는 JSON 데이터를 CommentEntity 객체로 변환합니다.
    // 해당 comment_id에 해당하는 댓글을 찾아 내용을 수정합니다.
    // 댓글을 저장하고 반환합니다.
    // 댓글이 없을 경우, RuntimeException 예외를 발생시킵니다.
    @PutMapping("/boards/{post_id}/comment/{comment_id}")
    public CommentEntity updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody CommentEntity commentDetails) {
        postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("Post not found")); // 게시글 존재 여부만 확인합니다.
        CommentEntity comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User currentUser = userService.getCurrentUser();
        // 댓글 작성자와 현재 로그인된 사용자가 같은지 확인합니다.
        if (currentUser == null || !currentUser.getNickname().equals(comment.getWriter())) {
            // 댓글 작성자와 현재 로그인된 사용자가 다를 경우, RuntimeException 예외를 발생시킵니다.
            throw new RuntimeException("You are not authorized to edit this comment");
        }
        comment.setContent(commentDetails.getContent());
        // 필요한 경우, 여기에서 댓글 작성자 등 다른 필드도 업데이트 할 수 있습니다.
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    // @PathVariable 어노테이션을 사용하여 URL 경로에 있는 comment_id 값을 받아올 수 있습니다.
    // 해당 comment_id에 해당하는 댓글을 삭제합니다.
    // 댓글이 없을 경우, RuntimeException 예외를 발생시킵니다.
    // 댓글이 있을 경우, 댓글을 삭제합니다.
    @DeleteMapping("/boards/{post_id}/comment/{comment_id}")
    public void deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id) {
        postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("Post not found")); // 게시글 존재 여부만 확인합니다.
        CommentEntity comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User currentUser = userService.getCurrentUser();
        // 댓글 작성자와 현재 로그인된 사용자가 같은지 확인합니다.
        if (currentUser == null || !currentUser.getNickname().equals(comment.getWriter())) {
            // 댓글 작성자와 현재 로그인된 사용자가 다를 경우, RuntimeException 예외를 발생시킵니다.
            throw new RuntimeException("You are not authorized to delete this comment");
        }
        commentRepository.deleteById(comment_id);


    }
}
