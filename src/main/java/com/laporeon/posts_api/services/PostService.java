package com.laporeon.posts_api.services;

import com.laporeon.posts_api.dto.PostDTO;
import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.exceptions.PostNotFoundException;
import com.laporeon.posts_api.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(PostDTO postDTO) {
        Post post = Post.fromDTO(postDTO);
        return postRepository.save(post);
    }

    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found."));
    }

    public List<Post> getAllPosts() {
        List<Post> postList = postRepository.findAll();

        if(postList.isEmpty()) {
            throw new PostNotFoundException("No posts found.");
        }

        return postList;
    }

    public void deletePost(String id) {
        postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found."));

        postRepository.deleteById(id);
    }

}
