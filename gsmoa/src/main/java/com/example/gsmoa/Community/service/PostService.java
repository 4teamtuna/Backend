package com.example.gsmoa.Community.service;

import com.example.gsmoa.Community.entity.PostEntity;
import com.example.gsmoa.Community.repository.PostNotFoundException;
import com.example.gsmoa.Community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeService postLikeService;

    @Autowired
    public PostService(PostRepository postRepository, PostLikeService postLikeService) {
        this.postRepository = postRepository;
        this.postLikeService = postLikeService;
    }

    public PostEntity createPost(PostEntity board) {
        return postRepository.save(board);
    }

    // PostService.java
    public List<PostEntity> getAllPosts(Long userId) {
        List<PostEntity> posts = postRepository.findAll();
        for (PostEntity post : posts) {
            boolean isLiked = postLikeService.isLiked(userId, post.getPostId());
            int likesCount = postLikeService.getPostLikesCount(post.getPostId());
            post.setLiked(isLiked);
            post.setLikes(likesCount);
        }
        return posts;
    }

    public PostEntity getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public PostEntity updatePost(Long id, PostEntity newPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(newPost.getTitle());
                    post.setContent(newPost.getContent());
                    return postRepository.save(post);
                })
                .orElseGet(() -> {
                    newPost.setPostId(id);
                    return postRepository.save(newPost);
                });
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}