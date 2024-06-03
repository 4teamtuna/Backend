package com.example.gsmoa.Community.service;

import com.example.gsmoa.Community.entity.CommentEntity;
import com.example.gsmoa.Community.repository.CommentRepository;
import com.example.gsmoa.User.entity.UserEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.example.gsmoa.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, SimpMessagingTemplate simpMessagingTemplate, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.messagingTemplate = simpMessagingTemplate;
        this.userRepository = userRepository;
    }

    public CommentEntity createComment(CommentEntity comment, Long loggedInUserId) {
        UserEntity loggedInUser = userRepository.findById(loggedInUserId.intValue()).orElseThrow();
        comment.setPostOwner(loggedInUser);
        comment.setWriterId(loggedInUser.getNickname());
        CommentEntity savedComment = commentRepository.save(comment);
        if (loggedInUser.equals(savedComment.getPostOwner())){
            messagingTemplate.convertAndSendToUser(
                    loggedInUser.getUsername(),
                    "/queue/notification",
                    "New comment on your post: " + savedComment.getContent()
            );
        }
        // 게시글 작성자에게 알림을 보냅니다.
        UserEntity postOwner = savedComment.getPost().getPostOwner();
        if (!postOwner.equals(loggedInUser)) {
            messagingTemplate.convertAndSendToUser(
                    postOwner.getUsername(),
                    "/queue/notification",
                    "New comment on your post: " + savedComment.getContent()
            );
        }
        return savedComment;
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