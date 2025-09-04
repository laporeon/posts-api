package com.laporeon.posts_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(
        @NotBlank(message = "Title is required.")
        @Size(min = 3, max = 100, message = "Title must be between {min} and {max} characters long.")
        String title,
        @NotBlank(message = "Description is required.")
        @Size(min = 10, max = 250, message = "Description must be between {min} and {max} characters long.")
        String description,
        @NotBlank(message = "Body content is required.")
        @Size(min = 10, max = 500, message = "Body content must be between {min} and {max} characters long.")
        String body
) {}