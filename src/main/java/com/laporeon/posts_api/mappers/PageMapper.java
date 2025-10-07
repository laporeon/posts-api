package com.laporeon.posts_api.mappers;

import com.laporeon.posts_api.dto.response.PageResponseDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.entities.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostPaginationMapper {
    private final PostMapper postMapper;

    public PageResponseDTO<PostResponseDTO> toDTO(Page<Post> posts) {
        List<PostResponseDTO> content = posts.getContent()
                                                 .stream()
                                                 .map(postMapper::toDTO)
                                                 .toList();

        return new PageResponseDTO<>(
                content,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalPages(),
                posts.getTotalElements(),
                posts.getNumberOfElements(),
                posts.isFirst(),
                posts.isLast(),
                posts.isEmpty(),
                posts.getSort().isSorted(),
                posts.getSort().isUnsorted()
        );
    }
}