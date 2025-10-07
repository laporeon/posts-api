package com.laporeon.posts_api.services;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.dto.response.PageResponseDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.exceptions.PostNotFoundException;
import com.laporeon.posts_api.mappers.PostMapper;
import com.laporeon.posts_api.mappers.PageMapper;
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

    private final PageMapper paginationMapper;

    public PostResponseDTO create(PostRequestDTO dto) {
        Post post = postRepository.save(postMapper.toEntity(dto));
        return postMapper.toDTO(post);
    }

    public PageResponseDTO<PostResponseDTO> listPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        PageResponseDTO<PostResponseDTO> pageResponseDTO = paginationMapper.toDTO(posts);
        return pageResponseDTO;
    }

    public PostResponseDTO findById(String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        return postMapper.toDTO(post);
    }

    public PostResponseDTO update(String id, PostRequestDTO dto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        post = postRepository.save(postMapper.updateEntityFromDTO(dto, post));

        return postMapper.toDTO(post);
    }

    public void delete(String id) {
        postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        postRepository.deleteById(id);
    }

}
