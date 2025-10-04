package com.laporeon.posts_api.commom;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.entities.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Constants {


    public static final String INVALID_POST_ID = "68e0124a70424186e056e45d";

    public static final PostRequestDTO VALID_POST_REQUEST_DTO = new PostRequestDTO("" +
            "Getting Started with Spring Boot",
            "A comprehensive guide to building REST APIs with Spring Boot framework.",
            "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.");

    public static final PostRequestDTO INVALID_POST_REQUEST_DTO = new PostRequestDTO("", "", "");

    public static final Post VALID_POST_1 = new Post("68e0037161ed314180e6b6ce",
            "MongoDB with Spring Data",
            "Learn how to integrate MongoDB with Spring Data for seamless database operations.",
            "MongoDB is a popularNoSQL database that works perfectly with Spring Boot applications.",
            LocalDateTime.of(2025, 1, 25, 8, 54),
            LocalDateTime.of(2025, 1, 25, 8, 54));

    public static final Post VALID_POST_2 = new Post("507f1f77bcf86cd799439013",
            "API Documentation with Swagger",
            "Best practices for documenting REST APIs using OpenAPI and Swagger UI.",
            "Good API documentation is crucial for developer experience.",
            LocalDateTime.of(2025, 2, 10, 10, 23),
            LocalDateTime.of(2025, 2, 10, 10, 23));

    public static final List<Post> posts = new ArrayList<>() {
        {
            add(VALID_POST_1);
            add(VALID_POST_2);
        }
    };

    public static final Post EXPECTED_SAVED_POST = new Post(
            "507f1f77bcf86cd799439013",
            VALID_POST_REQUEST_DTO.title(),
            VALID_POST_REQUEST_DTO.description(),
            VALID_POST_REQUEST_DTO.body(),
            LocalDateTime.of(2025, 10, 3, 15, 55),
            LocalDateTime.of(2025, 10, 3, 15, 55));

}
