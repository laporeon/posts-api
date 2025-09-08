package com.laporeon.posts_api.dto.response;

import java.time.LocalDateTime;

public record PostResponseDTO(
        String id,
        String title,
        String description,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
