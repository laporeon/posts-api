package com.laporeon.posts_api.controllers;

import com.laporeon.posts_api.dto.response.PageablePostResponseDTO;
import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
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
    public ResponseEntity<PageablePostResponseDTO> getAllPosts(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "title") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), orderBy));

        Page<Post> posts = postService.getAllPosts(pageable);
        PageablePostResponseDTO<Post> response = mapper.toDto(posts);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> findPostById(@PathVariable("id") String id) {
        Post post = postService.findById(id);
        PostResponseDTO postResponseDTO = new PostResponseDTO(post.getId(), post.getTitle(),
                post.getDescription(), post.getBody(), post.getCreatedAt(), post.getUpdatedAt());
        return ResponseEntity.ok().body(postResponseDTO);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO postRequestDTO) {
        Post post = postService.create(postRequestDTO);
        PostResponseDTO postResponseDTO = new PostResponseDTO(post.getId(), post.getTitle(),
                post.getDescription(), post.getBody(), post.getCreatedAt(), post.getUpdatedAt());
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable("id") String id, @Valid @RequestBody PostRequestDTO postRequestDTO) {
        Post post = postService.updatePost(id, postRequestDTO);
        PostResponseDTO postResponseDTO = new PostResponseDTO(post.getId(), post.getTitle(),
                post.getDescription(), post.getBody(), post.getCreatedAt(), post.getUpdatedAt());
        return ResponseEntity.ok().body(postResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
