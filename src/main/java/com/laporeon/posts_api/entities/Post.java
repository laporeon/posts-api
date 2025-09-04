package com.laporeon.posts_api.entities;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "posts")
@Data
public class Post {

    @MongoId
    public String id;

    public String title;

    public String description;

    public String body;

    @CreatedDate
    @Field("created_at")
    public LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    public LocalDateTime updatedAt;

    public static Post fromDTO(PostRequestDTO postRequestDTO) {
        Post post = new Post();
        post.setTitle(postRequestDTO.title());
        post.setDescription(postRequestDTO.description());
        post.setBody(postRequestDTO.body());
        return post;
    }
}
