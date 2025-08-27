package com.laporeon.posts_api.repositories;

import com.laporeon.posts_api.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
