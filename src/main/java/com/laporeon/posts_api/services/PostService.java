package com.laporeon.posts_api.services;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.exceptions.PostNotFoundException;
import com.laporeon.posts_api.mappers.PostMapper;
import com.laporeon.posts_api.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Post create(PostRequestDTO postRequestDTO) {
        Post post = postMapper.toEntity(postRequestDTO);
        return postRepository.save(post);
    }

    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post updatePost(String id, PostRequestDTO postRequestDTO) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        
        existingPost.setTitle(postRequestDTO.title());
        existingPost.setDescription(postRequestDTO.description());
        existingPost.setBody(postRequestDTO.body());

        return postRepository.save(existingPost);
    }

    public void deletePost(String id) {
        postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        postRepository.deleteById(id);
    }

}
