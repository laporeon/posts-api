package com.laporeon.posts_api.services;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.exceptions.PostNotFoundException;
import com.laporeon.posts_api.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(PostRequestDTO postRequestDTO) {
        Post post = Post.fromDTO(postRequestDTO);
        return postRepository.save(post);
    }

    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        Page<Post> postList = postRepository.findAll(pageable);

        return postList;
    }

    public Post updatePost(String id, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        Post updatedPost = Post.fromDTO(postRequestDTO);
        updatedPost.setId(post.getId());
        updatedPost.setCreatedAt(post.getCreatedAt());

        return postRepository.save(updatedPost);
    }

    public void deletePost(String id) {
        postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        postRepository.deleteById(id);
    }

}
