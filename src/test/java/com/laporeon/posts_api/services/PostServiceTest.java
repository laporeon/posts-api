package com.laporeon.posts_api.services;

import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.dto.response.PageResponseDTO;
import com.laporeon.posts_api.dto.response.PostResponseDTO;
import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.exceptions.PostNotFoundException;
import com.laporeon.posts_api.mappers.PostMapper;
import com.laporeon.posts_api.mappers.PageMapper;
import com.laporeon.posts_api.repositories.PostRepository;

import static com.laporeon.posts_api.commom.Constants.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private PageMapper pageMapper;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("Should return saved post when given valid data")
    void create_ShouldReturnSavedPost_WhenGivenValidData() {
        when(postMapper.toEntity(any(PostRequestDTO.class))).thenReturn(VALID_POST_ENTITY);
        when(postRepository.save(any(Post.class))).thenReturn(SAVED_POST_ENTITY);
        when(postMapper.toDTO(any(Post.class))).thenReturn(SAVED_POST_RESPONSE_DTO);

        PostResponseDTO sut = postService.create(VALID_POST_REQUEST_DTO);

        assertThat(sut.id()).isEqualTo(SAVED_POST_RESPONSE_DTO.id());
        assertThat(sut.title()).isEqualTo(SAVED_POST_RESPONSE_DTO.title());

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("Should return page of posts when given valid pageable")
    void listPosts_ShouldReturnPageOfPosts_WhenGivenValidPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> expectedPage = new PageImpl<>(POSTS_ENTITY_LIST, pageable, POSTS_ENTITY_LIST.size());

        when(pageMapper.toDTO(any(Page.class))).thenReturn(POSTS_RESPONSE_PAGE);
        when(postRepository.findAll(pageable)).thenReturn(expectedPage);

        PageResponseDTO<PostResponseDTO> sut = postService.listPosts(pageable);

        assertThat(sut.totalElements()).isEqualTo(POSTS_RESPONSE_PAGE.totalElements());
        assertThat(sut.content()).hasSize(POSTS_RESPONSE_PAGE.content().size());

        verify(postRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should return post when given existing id")
    void findById_ShouldReturnPost_WhenGivenExistingId() {
        when(postRepository.findById(VALID_POST_ENTITY.getId())).thenReturn(Optional.of(VALID_POST_ENTITY));
        when(postMapper.toDTO(any(Post.class))).thenReturn(SAVED_POST_RESPONSE_DTO);

        PostResponseDTO sut = postService.findById(VALID_POST_ENTITY.getId());

        assertThat(sut.id()).isEqualTo(SAVED_POST_RESPONSE_DTO.id());
        assertThat(sut.title()).isEqualTo(SAVED_POST_RESPONSE_DTO.title());

        verify(postRepository, times(1)).findById(VALID_POST_ENTITY.getId());
    }

    @Test
    @DisplayName("Should throw PostNotFoundException when id does not exist")
    void findById_ShouldThrowPostNotFoundException_WhenIdDoesNotExist() {
        when(postRepository.findById(INVALID_POST_ID)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.findById(INVALID_POST_ID));

        verify(postRepository, times(1)).findById(INVALID_POST_ID);
    }


    @Test
    @DisplayName("Should return updated post when given valid data and existing id")
    void update_ShouldReturnUpdatedPost_WhenGivenValidDataAndExistingId() {
        when(postRepository.findById(VALID_POST_ENTITY.getId())).thenReturn(Optional.of(VALID_POST_ENTITY));
        when(postMapper.updateEntityFromDTO(any(PostRequestDTO.class), any(Post.class))).thenReturn(SAVED_POST_ENTITY);
        when(postMapper.toDTO(any(Post.class))).thenReturn(SAVED_POST_RESPONSE_DTO);
        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        PostResponseDTO sut = postService.update(VALID_POST_ENTITY.getId(), VALID_POST_REQUEST_DTO);

        assertThat(sut.id()).isEqualTo(SAVED_POST_RESPONSE_DTO.id());
        assertThat(sut.title()).isEqualTo(SAVED_POST_RESPONSE_DTO.title());
        assertThat(sut.createdAt()).isEqualTo(SAVED_POST_RESPONSE_DTO.createdAt());

        verify(postRepository, times(1)).findById(VALID_POST_ENTITY.getId());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("Should throw PostNotFoundException when updating non-existent post")
    void update_ShouldThrowPostNotFoundException_WhenUpdatingNonExistentPost() {
        when(postRepository.findById(INVALID_POST_ID)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.update(INVALID_POST_ID, VALID_POST_REQUEST_DTO));

        verify(postRepository, times(1)).findById(INVALID_POST_ID);
    }

    @Test
    @DisplayName("Should delete post when given existing id")
    void delete_ShouldDeletePost_WhenGivenExistingId() {
        when(postRepository.findById(VALID_POST_ENTITY.getId())).thenReturn(Optional.of(VALID_POST_ENTITY));

        doNothing().when(postRepository).deleteById(VALID_POST_ENTITY.getId());

        postService.delete(VALID_POST_ENTITY.getId());

        verify(postRepository, times(1)).findById(VALID_POST_ENTITY.getId());
        verify(postRepository, times(1)).deleteById(VALID_POST_ENTITY.getId());
    }

    @Test
    @DisplayName("Should throw PostNotFoundException when deleting non-existent post")
    void delete_ShouldThrowPostNotFoundException_WhenDeletingNonExistentPost() {
        when(postRepository.findById(INVALID_POST_ID)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.delete(INVALID_POST_ID));

        verify(postRepository, times(1)).findById(INVALID_POST_ID);
        verify(postRepository, never()).deleteById(INVALID_POST_ID);
    }
}
