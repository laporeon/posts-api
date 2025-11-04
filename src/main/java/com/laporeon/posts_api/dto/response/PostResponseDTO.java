package com.laporeon.posts_api.dto.response;

import java.time.Instant;

public record PostResponseDTO(
        String id,
        String title,
        String description,
        String body,
        Instant createdAt,
        Instant updatedAt
) {
}
