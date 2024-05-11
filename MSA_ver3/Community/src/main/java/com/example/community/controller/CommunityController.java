package com.example.community.controller;

import com.example.community.entity.CommentEntity;
import com.example.community.entity.PostEntity;
import com.example.community.service.PostService;
import com.example.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/main/community")
public class CommunityController {

    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public CommunityController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/post")
    public PostEntity createPost(@RequestBody PostEntity board) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        board.setWriterId(username);
        return postService.createPost(board);
    }

    @GetMapping("/list")
    public List<PostEntity> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostEntity getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PutMapping("/{postId}")
    public PostEntity updatePost(@PathVariable Long postId, @RequestBody PostEntity post) {
        return postService.updatePost(postId, post);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @PostMapping("/{postId}/comment")
    public CommentEntity createComment(@PathVariable Long postId, @RequestBody CommentEntity comment) {
        PostEntity post = postService.getPost(postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setWriterId(username);
        comment.setPost(post);
        return commentService.createComment(comment);
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public CommentEntity updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentEntity comment) {
        return commentService.updateComment(commentId, comment);
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
