package com.laporeon.posts_api.services;

import com.laporeon.posts_api.dto.PostDTO;
import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(PostDTO postDTO) {
        Post post = Post.fromDTO(postDTO);
        return postRepository.save(post);
    }

}
