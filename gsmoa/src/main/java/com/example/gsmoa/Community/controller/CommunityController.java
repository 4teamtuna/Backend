package com.example.gsmoa.Community.controller;

import com.example.gsmoa.Community.entity.CommentEntity;
import com.example.gsmoa.Community.entity.PostEntity;
import com.example.gsmoa.Community.service.PostService;
import com.example.gsmoa.Community.service.CommentService;
import com.example.gsmoa.User.dto.CustomUserDetails;
import com.example.gsmoa.User.entity.UserEntity;
import com.example.gsmoa.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/main/community")
public class CommunityController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;


    @Autowired
    public CommunityController(PostService postService, CommentService commentService, UserRepository userRepository) {
        this.postService = postService;
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    @PostMapping("/post")
    public PostEntity createPost(@RequestBody PostEntity board) {
        // Get the nickname of the currently logged in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        // Set the writerId of the board to the nickname
        board.setWriterId(user.getNickname());
        // Create the post
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
        UserEntity user = userRepository.findByUsername(username);
        comment.setWriterId(user.getNickname());
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