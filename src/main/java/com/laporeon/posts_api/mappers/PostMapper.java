package com.laporeon.posts_api.mappers;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponseDTO toDto(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getBody(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public Post fromDto(PostRequestDTO dto) {
        Post post = new Post();
        post.setTitle(dto.title());
        post.setDescription(dto.description());
        post.setBody(dto.body());
        return post;
    }

    public void updateEntity(Post post, PostRequestDTO dto) {
        post.setTitle(dto.title());
        post.setDescription(dto.description());
        post.setBody(dto.body());
    }
}
