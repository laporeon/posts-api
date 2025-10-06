package com.laporeon.posts_api.commom;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.dto.response.PageablePostResponseDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

public class Constants {

    public static final String INVALID_POST_ID = "68e0124a70424186e056e45d";

    public static final PostRequestDTO INVALID_POST_REQUEST_DTO = new PostRequestDTO("", "", "");

    public static final Post VALID_POST_ENTITY = new Post(
            "68e0037161ed314180e6b6ce",
            "Getting Started with Spring Boot",
            "A comprehensive guide to building REST APIs with Spring Boot framework.",
            "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.",
            LocalDateTime.of(2025, 1, 25, 8, 54),
            LocalDateTime.of(2025, 1, 25, 8, 54));

    public static final List<Post> POSTS_ENTITY_LIST = List.of(VALID_POST_ENTITY);

    public static final PostRequestDTO VALID_POST_REQUEST_DTO = new PostRequestDTO(
            VALID_POST_ENTITY.getTitle(),
            VALID_POST_ENTITY.getDescription(),
            VALID_POST_ENTITY.getBody());

    public static final Post SAVED_POST_ENTITY = new Post(
            "68e0037161ed314180e6b6ce",
            VALID_POST_REQUEST_DTO.title(),
            VALID_POST_REQUEST_DTO.description(),
            VALID_POST_REQUEST_DTO.body(),
            LocalDateTime.of(2025, 10, 3, 15, 55),
            LocalDateTime.of(2025, 10, 3, 15, 55));

    public static final PostResponseDTO SAVED_POST_RESPONSE_DTO = new PostResponseDTO(
            SAVED_POST_ENTITY.getId(),
            SAVED_POST_ENTITY.getTitle(),
            SAVED_POST_ENTITY.getDescription(),
            SAVED_POST_ENTITY.getBody(),
            SAVED_POST_ENTITY.getCreatedAt(),
            SAVED_POST_ENTITY.getUpdatedAt());

    public static final List<PostResponseDTO> POSTS_RESPONSE_CONTENT = POSTS_ENTITY_LIST.stream()
                                                                         .map(post -> new PostResponseDTO(
                                                                                 post.getId(),
                                                                                 post.getTitle(),
                                                                                 post.getDescription(),
                                                                                 post.getBody(),
                                                                                 post.getCreatedAt(),
                                                                                 post.getUpdatedAt()))
                                                                         .toList();

    public static final PageablePostResponseDTO<PostResponseDTO> POSTS_RESPONSE_PAGE = new PageablePostResponseDTO<>(
            POSTS_RESPONSE_CONTENT,
            0,
            POSTS_RESPONSE_CONTENT.size(),
            1,
            POSTS_RESPONSE_CONTENT.size(),
            POSTS_RESPONSE_CONTENT.size(),
            true,
            true,
            POSTS_RESPONSE_CONTENT.isEmpty(),
            true,
            false
    );
}
