package com.laporeon.posts_api.controllers;

import com.laporeon.posts_api.docs.PostRequestDTOExample;
import com.laporeon.posts_api.docs.SwaggerExamples;
import com.laporeon.posts_api.dto.response.PageResponseDTO;
import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.dto.response.ErrorResponseDTO;
import com.laporeon.posts_api.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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


@Tag(name = "Posts", description = "Endpoints for managing blog posts")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostRequestDTOExample
    @Operation(
            summary = "Create a new post",
            description = "Creates a new post with specified title, description and content. Validates input and returns saved post.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.SINGLE_POST_RESPONSE_EXAMPLE))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.VALIDATION_ERROR_MESSAGE)))
            }
    )
    @PostMapping
    public ResponseEntity<PostResponseDTO> create(@Valid @RequestBody PostRequestDTO dto) {
        PostResponseDTO postResponseDTO = postService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
    }

    @Operation(
            summary = "List all posts",
            description = "Returns a paginated and sorted list of posts, allowing control over page number, size, order by field, and sort direction.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.POSTS_LIST_RESPONSE_EXAMPLE)))
            }
    )
    @GetMapping
    public ResponseEntity<PageResponseDTO> listPosts(
            @Parameter(description = "Page number")
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(description = "Field to sort by")
            @RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
            @Parameter(description = "Sort direction (ASC or DESC)")
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), orderBy));

        PageResponseDTO<PostResponseDTO> posts = postService.listPosts(pageable);
        return ResponseEntity.ok().body(posts);
    }

    @Operation(
            summary = "Get post by ID",
            description = "Fetches a post by its unique ID. Returns 404 error if post does not exist.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.SINGLE_POST_RESPONSE_EXAMPLE))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND_ERROR_MESSAGE)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> findPostById(@PathVariable("id") String id) {
        PostResponseDTO postResponseDTO = postService.findById(id);
        return ResponseEntity.ok().body(postResponseDTO);
    }

    @PostRequestDTOExample
    @Operation(
            summary = "Update existing post",
            description = "Updates the title, description, or content of an existing post identified by ID. Validates input and handles 404 if ID not found.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.SINGLE_POST_RESPONSE_EXAMPLE))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.VALIDATION_ERROR_MESSAGE))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND_ERROR_MESSAGE)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> update(
            @PathVariable("id") String id,
            @Valid @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.update(id, postRequestDTO);
        return ResponseEntity.ok().body(postResponseDTO);
    }

    @Operation(
            summary = "Delete post",
            description = "Deletes a post based on its unique ID. Returns 404 if post not found.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND_ERROR_MESSAGE)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
