package com.laporeon.posts_api.entities;

import com.laporeon.posts_api.dto.PostDTO;
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

    public static Post fromDTO(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.title());
        post.setDescription(postDTO.description());
        post.setBody(postDTO.body());
        return post;
    }
}
