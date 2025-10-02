package com.laporeon.posts_api.mappers;

import com.laporeon.posts_api.dto.response.PageablePostResponseDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.entities.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostPageMapper {
    private final PostMapper postMapper;

    public PageablePostResponseDTO<PostResponseDTO> toDTO(Page<Post> pagePosts) {
        List<PostResponseDTO> content = pagePosts.getContent()
                                                 .stream()
                                                 .map(postMapper::toDTO)
                                                 .toList();

        return new PageablePostResponseDTO<>(
                content,
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