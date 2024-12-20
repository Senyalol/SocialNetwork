package com.SocialNet.SocialNetwork.Controller;

import com.SocialNet.SocialNetwork.Service.PostService;
import com.SocialNet.SocialNetwork.DTO.PostDTO.CreatePostDTO;
import com.SocialNet.SocialNetwork.DTO.PostDTO.ShortPostInfoDTO;
import com.SocialNet.SocialNetwork.DTO.PostDTO.UpdatePostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Method to get all posts by userId
    @GetMapping
    public List<ShortPostInfoDTO> getPostsByUserId(@RequestParam int userId) {
        return postService.getPostsByUserId(userId);
    }

    // Method to create a new post
    @PostMapping
    public ShortPostInfoDTO createPost(@RequestBody @Valid CreatePostDTO createPostDTO) {
        return postService.createPost(createPostDTO);
    }

    // Method to update an existing post by ID
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable("id") int id, @Valid @RequestBody UpdatePostDTO updatePostDTO) {
        try {
            postService.updatePost(id, updatePostDTO);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Method to delete a post by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") int id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}