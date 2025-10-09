package com.laporeon.posts_api.dto.response;

import java.time.Instant;
import java.util.List;

public record ErrorResponseDTO(int status, String error, List<String> messages, Instant timestamp) {
}
