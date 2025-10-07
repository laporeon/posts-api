package com.laporeon.posts_api.dto.response;

import java.util.List;

public record PageResponseDTO<T>(List<T> content,
                                 int pageNumber,
                                 int pageSize,
                                 int totalPages,
                                 long totalElements,
                                 int numberOfElements,
                                 boolean isFirstPage,
                                 boolean isLastPage,
                                 boolean isEmpty,
                                 boolean isSorted,
                                 boolean isUnsorted) {
}