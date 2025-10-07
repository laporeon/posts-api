package com.laporeon.posts_api.controllers;

import com.laporeon.posts_api.docs.PostRequestDTOExample;
import com.laporeon.posts_api.docs.SwaggerExamples;
import com.laporeon.posts_api.dto.response.PageResponseDTO;
import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.dto.response.ErrorResponseDTO;
import com.laporeon.posts_api.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Posts")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostRequestDTOExample
    @Operation(summary = "Create a new post.")
    @ApiResponse(
            responseCode = "201",
            description = "Post successfully created.",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostResponseDTO.class),
                    examples = @ExampleObject(value = SwaggerExamples.SINGLE_POST_RESPONSE_EXAMPLE)))
    @ApiResponse(
            responseCode = "400",
            description = "Validation failed",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                            value = SwaggerExamples.VALIDATION_ERROR_MESSAGE)))
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO dto) {
        PostResponseDTO postResponseDTO = postService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
    }

    @Operation(summary = "Retrieve existing posts.")
    @ApiResponse(
            responseCode = "200",
            description = "Get a list of posts sorted alphabetically by title (A-Z).",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageResponseDTO.class),
                    examples = @ExampleObject(value = SwaggerExamples.POSTS_LIST_RESPONSE_EXAMPLE)))
    @GetMapping
    public ResponseEntity<PageResponseDTO> listPosts(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "title") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), orderBy));

        PageResponseDTO<PostResponseDTO> posts = postService.listPosts(pageable);
        return ResponseEntity.ok().body(posts);
    }

    @Operation(summary = "Retrieve an existing post.")
    @ApiResponse(
            responseCode = "200",
            description = "Retrieve an existing post by its id.",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostResponseDTO.class),
                    examples = @ExampleObject(value = SwaggerExamples.SINGLE_POST_RESPONSE_EXAMPLE)))
    @ApiResponse(
            responseCode = "404",
            description = "Post not found.",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND_ERROR_MESSAGE)))
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> findPostById(@PathVariable("id") String id) {
        PostResponseDTO postResponseDTO = postService.findById(id);
        return ResponseEntity.ok().body(postResponseDTO);
    }

    @PostRequestDTOExample
    @Operation(summary = "Update an existing post.")
    @ApiResponse(
            responseCode = "200",
            description = "Post successfully updated.",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostResponseDTO.class),
                    examples = @ExampleObject(value = SwaggerExamples.SINGLE_POST_RESPONSE_EXAMPLE)))
    @ApiResponse(
            responseCode = "400",
            description = "Validation failed",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                            value = SwaggerExamples.VALIDATION_ERROR_MESSAGE)))
    @ApiResponse(
            responseCode = "404",
            description = "Post not found.",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND_ERROR_MESSAGE)))
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable("id") String id, @Valid @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.update(id, postRequestDTO);
        return ResponseEntity.ok().body(postResponseDTO);
    }

    @Operation(summary = "Delete an existing post.")
    @ApiResponse(
            responseCode = "204",
            description = "Post successfully deleted.")
    @ApiResponse(
            responseCode = "404",
            description = "Post not found.",
            content =
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND_ERROR_MESSAGE)))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
