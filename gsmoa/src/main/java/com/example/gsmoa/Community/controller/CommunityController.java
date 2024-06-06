package com.example.gsmoa.Community.controller;

import com.example.gsmoa.Community.entity.CommentEntity;
import com.example.gsmoa.Community.entity.Notification;
import com.example.gsmoa.Community.entity.PostEntity;
import com.example.gsmoa.Community.entity.PostLike;
import com.example.gsmoa.Community.service.NotificationService;
import com.example.gsmoa.Community.service.PostLikeService;
import com.example.gsmoa.Community.service.PostService;
import com.example.gsmoa.Community.service.CommentService;
import com.example.gsmoa.User.dto.CustomUserDetails;
import com.example.gsmoa.User.entity.UserEntity;
import com.example.gsmoa.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main/community")
public class CommunityController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final PostLikeService postLikeService;
    private final NotificationService notificationService;
    // CommunityController.java
    private final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());


    @Autowired
    public CommunityController(PostService postService, CommentService commentService, UserRepository userRepository, PostLikeService postLikeService, NotificationService notificationService) {        this.postService = postService;
        this.commentService = commentService;
        this.userRepository = userRepository;
        this.postLikeService = postLikeService;
        this.notificationService = notificationService;
    }

//    @PostMapping("/post")
//    public PostEntity createPost(@RequestBody PostEntity board) {
//        // Get the nickname of the currently logged in user
////        String username = SecurityContextHolder.getContext().getAuthentication().getName();
////        UserEntity user = userRepository.findByUsername(username);
////        // Set the writerId of the board to the nickname
////        board.setWriterId(user.getNickname());
//        // Create the post
//        return postService.createPost(board);
//    }

    // CommunityController.java
    @GetMapping("/sse")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        // Add the emitter to a list of active emitters
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    @PostMapping("/post")
    public PostEntity createPost(@RequestBody PostEntity board) {
        // Get the username of the currently logged in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username);
        board.setWriterId(user.getNickname());
        // Set the writer of the post to the user
        board.setWriter(user);
        // Save the post
        return postService.createPost(board);
    }

    @GetMapping("/list")
    public List<PostEntity> getAllPosts(@RequestParam Long userId) {
        List<PostEntity> posts = postService.getAllPosts(userId);
        for(PostEntity post : posts) {
            boolean isLiked = postLikeService.isLiked(userId, post.getPostId());
            post.setLiked(isLiked);
        }
        return posts;
    }

    @GetMapping("/{postId}")
    public PostEntity getPost(@PathVariable Long postId) {
        PostEntity post = postService.getPost(postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        post.increaseHits();
        postService.createPost(post);

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

    @PostMapping("/{postId}/like")
    public PostEntity likePost(@PathVariable Long postId) {
        PostEntity post = postService.getPost(postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        post.increaseLikes(); // 좋아요 증가
        postService.createPost(post);
        return post;
    }

    @PostMapping("/{postId}/dislike")
    public PostEntity dislikePost(@PathVariable Long postId) {
        PostEntity post = postService.getPost(postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        post.decreaseLikes(); // 좋아요 증가
        postService.createPost(post);
        return post;
    }

//    @PostMapping("/{postId}/comment")
//    public CommentEntity createComment(@PathVariable Long postId, @RequestBody CommentEntity comment) {
//        PostEntity post = postService.getPost(postId);
//        if (post == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
//        }
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails userDetails) {
//            UserEntity user = userRepository.findByUsername(userDetails.getUsername());
//            post.setWriterId(user.getNickname());
//            comment.setPost(post);
//            return commentService.createComment(comment, (long) user.getId());
//        } else {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User details not found");
//        }
//    }
// CommunityController.java
    @PostMapping("/{postId}/comment")
    public CommentEntity createComment(@PathVariable Long postId, @RequestBody CommentEntity comment) {
    PostEntity post = postService.getPost(postId);
    if (post == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
    }
    UserEntity user = userRepository.findById(comment.getUserId().intValue()).orElseThrow();
    comment.setPostOwner(user);
    comment.setPost(post);
    Notification notification = new Notification();
    notification.setPostId(postId);
    notification.setChecked(false);
    notification.setCommentId(comment.getCommentId());
    notification.setPostOwnerId((long) post.getWriter().getId());
    notificationService.saveNotification(notification);

    return commentService.createComment(comment, (long) user.getId());
}


    @PutMapping("/{postId}/comment/{commentId}")
    public CommentEntity updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentEntity comment) {
        return commentService.updateComment(commentId, comment);
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PostMapping("/post/like")
    public PostLike likePost(@RequestBody PostLike post) {
        return postLikeService.savePostLike(post);
    }

    @DeleteMapping("/post/like")
    public void dislikePost(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long postId = Long.valueOf(params.get("postId").toString());
        postLikeService.removeLikedPost(userId, postId);
    }
    @GetMapping("/users/{userId}/comments/latest")
    public ResponseEntity<LocalDateTime> getLatestCommentTime(@PathVariable Long userId) {
        LocalDateTime latestCommentTime = commentService.getLatestCommentTimeByUser(userId);
        return ResponseEntity.ok(latestCommentTime);
    }

}