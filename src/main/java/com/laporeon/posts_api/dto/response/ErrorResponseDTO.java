package com.laporeon.posts_api.dto.response;

import java.time.Instant;

public record ErrorResponseDTO(int status,
                               String message,
                               Instant timestamp) {
}
