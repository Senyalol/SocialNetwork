package com.SocialNet.SocialNetwork.Service;

import com.SocialNet.SocialNetwork.DTO.PostDTO.CreatePostDTO;
import com.SocialNet.SocialNetwork.DTO.PostDTO.ShortPostInfoDTO;
import com.SocialNet.SocialNetwork.DTO.PostDTO.UpdatePostDTO;
import com.SocialNet.SocialNetwork.Entites.Post;
import com.SocialNet.SocialNetwork.Entites.User;
import com.SocialNet.SocialNetwork.Repository.PostRepository;
import com.SocialNet.SocialNetwork.Repository.UserRepository; // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // Add UserRepository

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository; // Initialize UserRepository
    }

    public List<ShortPostInfoDTO> getPostsByUserId(int userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(post -> {
                    ShortPostInfoDTO postDTO = new ShortPostInfoDTO();
                    postDTO.setPost_id(post.getId());
                    postDTO.setUser_id(post.getUser().getId());
                    postDTO.setText(post.getText());
                    postDTO.setImageUrl(post.getImageUrl());
                    postDTO.setCreatedAt(post.getCreatedAt());
                    return postDTO;
                })
                .collect(Collectors.toList());
    }

    public ShortPostInfoDTO createPost(CreatePostDTO createPostDTO) {
        User user = userRepository.findById(createPostDTO.getUser_id())
                .orElseThrow(() -> new NoSuchElementException("User with ID " + createPostDTO.getUser_id() + " not found")); // Fetch existing user

        Post post = new Post();
        post.setUser(user); // Set the existing user
        post.setText(createPostDTO.getText());
        post.setImageUrl(createPostDTO.getImageUrl());
        post.setCreatedAt(Instant.now()); // Set current time

        Post savedPost = postRepository.save(post);

        ShortPostInfoDTO postDTO = new ShortPostInfoDTO();
        postDTO.setPost_id(savedPost.getId());
        postDTO.setUser_id(savedPost.getUser().getId());
        postDTO.setText(savedPost.getText());
        postDTO.setImageUrl(savedPost.getImageUrl());
        postDTO.setCreatedAt(savedPost.getCreatedAt());

        return postDTO; // Return DTO with created post
    }

    public void updatePost(int id, UpdatePostDTO updatePostDTO) {
        Post postToUpdate = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post with ID " + id + " not found"));

        if (updatePostDTO.getText() != null) {
            postToUpdate.setText(updatePostDTO.getText());
        }
        if (updatePostDTO.getImageUrl() != null) {
            postToUpdate.setImageUrl(updatePostDTO.getImageUrl());
        }

        postRepository.save(postToUpdate);
    }

    public void deletePost(int id) {
        Post postToDelete = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post with ID " + id + " not found"));

        postRepository.delete(postToDelete);
    }
}