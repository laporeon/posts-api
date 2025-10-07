package com.laporeon.posts_api.exceptions;

import java.time.Instant;
import java.util.List;

public record APIErrorResponse(int status, String name, List<String> errors, Instant timestamp) {
}
