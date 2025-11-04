package com.laporeon.posts_api.mappers;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponseDTO toDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getBody(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public Post toEntity(PostRequestDTO dto) {
        return new Post()
                .builder()
                .title(dto.title())
                .description(dto.description())
                .body(dto.body())
                .build();
    }

    public Post updateEntityFromDTO(PostRequestDTO dto, Post post) {
        post.setTitle(dto.title());
        post.setDescription(dto.description());
        post.setBody(dto.body());
        return post;
    }
}
