package com.example.gsmoa.controller;

import com.example.gsmoa.entity.CommentEntity;
import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.repository.CommentRepository;
import com.example.gsmoa.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository postRepository;

    // 특정 게시글에 댓글 작성
    @PostMapping("/boards/{post_id}/comment")
    public CommentEntity addCommentToPost(@PathVariable Long post_id, @RequestBody CommentEntity comment) {
        BoardEntity post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    // 특정 댓글 수정
    @PutMapping("/boards/{post_id}/comment/{comment_id}")
    public CommentEntity updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentEntity commentDetails) {
        postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found")); // 게시글 존재 여부만 확인합니다.
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(commentDetails.getContent());
        // 필요한 경우, 여기에서 댓글 작성자 등 다른 필드도 업데이트 할 수 있습니다.

        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @DeleteMapping("/boards/{post_id}/comment/{comment_id}")
    public void deleteComment(@PathVariable Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
