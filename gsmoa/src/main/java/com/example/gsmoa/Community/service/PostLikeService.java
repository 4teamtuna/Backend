package com.example.gsmoa.Community.service;

import com.example.gsmoa.Community.entity.PostLike;
import com.example.gsmoa.Community.repository.PostLikeRepository;
import com.example.gsmoa.Community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    public PostLikeService(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }

    public PostLike savePostLike(PostLike postLike) {
        return postLikeRepository.save(postLike);
    }

    public void removeLikedPost(Long userId, Long postId) {
        PostLike postLike = postLikeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new IllegalArgumentException("No PostLike found with userId: " + userId + " and postId: " + postId));
        postLikeRepository.delete(postLike);
    }

    public List<PostLike> getLikedPosts(Long userId) {
        return postLikeRepository.findByUserId(userId);
    }

    public boolean isLiked(Long userId, Long postId) {
        return postLikeRepository.findByUserIdAndPostId(userId, postId).isPresent();
    }

    public int getPostLikesCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

}
