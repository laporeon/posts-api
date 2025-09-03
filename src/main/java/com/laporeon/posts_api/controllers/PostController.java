package com.laporeon.posts_api.controllers;

import com.laporeon.posts_api.dto.PageResponseDTO;
import com.laporeon.posts_api.dto.PostDTO;
import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.services.PostService;
import com.laporeon.posts_api.mappers.PostPageMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostPageMapper mapper;

    @GetMapping
    public ResponseEntity<PageResponseDTO> getAllPosts(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "title") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), orderBy));

        Page<Post> posts = postService.getAllPosts(pageable);
        PageResponseDTO<Post> response = mapper.toDto(posts);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> findPostById(@PathVariable("id") String id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO postDTO) {
        Post post = postService.create(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") String id, @Valid @RequestBody PostDTO postDTO) {
        Post post = postService.updatePost(id, postDTO);
        return ResponseEntity.ok().body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
