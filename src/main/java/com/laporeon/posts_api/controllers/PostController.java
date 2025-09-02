package com.laporeon.posts_api.controllers;

import com.laporeon.posts_api.dto.PostDTO;
import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // TODO: Create pagination for getAllPosts method
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findPostById(@PathVariable("id") String id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO postDTO) {
        Post post = postService.create(postDTO);
        return ResponseEntity.status(201).body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
