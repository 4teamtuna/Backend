package com.example.gsmoa.Community.service;

import com.example.gsmoa.Community.entity.CommentEntity;
import com.example.gsmoa.Community.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentEntity createComment(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public List<CommentEntity> getCommentsByBoardId(Long postId) {
        return commentRepository.findAllByPost_PostId(postId);
    }

    public CommentEntity updateComment(Long id, CommentEntity comment) {
        CommentEntity existingComment = commentRepository.findById(id).orElseThrow();
        existingComment.setContent(comment.getContent());
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}