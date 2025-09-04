package com.laporeon.posts_api.mappers;

import com.laporeon.posts_api.dto.response.PageablePostResponseDTO;
import com.laporeon.posts_api.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PostPageMapper {
    public PageablePostResponseDTO<Post> toDto(Page<Post> pagePosts) {
        return new PageablePostResponseDTO(
                pagePosts.getContent(),
                pagePosts.getNumber(),
                pagePosts.getSize(),
                pagePosts.getTotalPages(),
                pagePosts.getTotalElements(),
                pagePosts.getNumberOfElements(),
                pagePosts.isFirst(),
                pagePosts.isLast(),
                pagePosts.isEmpty(),
                pagePosts.getSort().isSorted(),
                pagePosts.getSort().isUnsorted()
        );
    }
}