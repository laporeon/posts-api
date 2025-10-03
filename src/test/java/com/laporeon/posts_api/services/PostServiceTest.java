package com.laporeon.posts_api.services;

import com.laporeon.posts_api.entities.Post;
import com.laporeon.posts_api.exceptions.PostNotFoundException;
import com.laporeon.posts_api.mappers.PostMapper;
import com.laporeon.posts_api.repositories.PostRepository;

import static com.laporeon.posts_api.commom.PostConstants.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("Should return saved post when given valid data")
    void create_ShouldReturnSavedPost_WhenGivenValidData() {
        when(postRepository.save(any(Post.class))).thenReturn(EXPECTED_SAVED_POST);

        Post sut = postService.create(VALID_POST_REQUEST_DTO);

        assertThat(sut.getId()).isEqualTo(EXPECTED_SAVED_POST.getId());
        assertThat(sut.getTitle()).isEqualTo(VALID_POST_REQUEST_DTO.title());

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("Should return post when given existing id")
    void findById_ShouldReturnPost_WhenGivenExistingId() {
        when(postRepository.findById(VALID_POST_1.getId())).thenReturn(Optional.of(VALID_POST_1));

        Post sut = postService.findById(VALID_POST_1.getId());

        assertThat(sut.getId()).isEqualTo(VALID_POST_1.getId());
        assertThat(sut.getTitle()).isEqualTo(VALID_POST_1.getTitle());

        verify(postRepository, times(1)).findById(VALID_POST_1.getId());
    }

    @Test
    @DisplayName("Should throw PostNotFoundException when id does not exist")
    void findById_ShouldThrowPostNotFoundException_WhenIdDoesNotExist() {
        when(postRepository.findById(INVALID_POST_ID)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> {
            postService.findById(INVALID_POST_ID);
        });

        verify(postRepository, times(1)).findById(INVALID_POST_ID);
    }

    @Test
    @DisplayName("Should return page of posts when given valid pageable")
    void getAllPosts_ShouldReturnPageOfPosts_WhenGivenValidPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> expectedPage = new PageImpl<>(posts);

        when(postRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Post> sut = postService.getAllPosts(pageable);

        assertThat(sut.getTotalPages()).isEqualTo(1);
        assertThat(sut.getTotalElements()).isEqualTo(2);
        assertThat(sut.getContent()).containsExactly(VALID_POST_1, VALID_POST_2);

        verify(postRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return updated post when given valid data and existing id")
    void updatePost_ShouldReturnUpdatedPost_WhenGivenValidDataAndExistingId() {
        when(postRepository.findById(VALID_POST_1.getId())).thenReturn(Optional.of(VALID_POST_1));

        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Post sut = postService.updatePost(VALID_POST_1.getId(), VALID_POST_REQUEST_DTO);

        assertThat(sut.getId()).isEqualTo(VALID_POST_1.getId());
        assertThat(sut.getTitle()).isEqualTo(VALID_POST_REQUEST_DTO.title());
        assertThat(sut.getCreatedAt()).isEqualTo(VALID_POST_1.getCreatedAt());

        verify(postRepository, times(1)).findById(VALID_POST_1.getId());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("Should throw PostNotFoundException when updating non-existent post")
    void updatePost_ShouldThrowPostNotFoundException_WhenUpdatingNonExistentPost() {
        when(postRepository.findById(INVALID_POST_ID)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> {
            postService.updatePost(INVALID_POST_ID, VALID_POST_REQUEST_DTO);
        });

        verify(postRepository, times(1)).findById(INVALID_POST_ID);
    }

    @Test
    @DisplayName("Should delete post when given existing id")
    void deletePost_ShouldDeletePost_WhenGivenExistingId() {
        when(postRepository.findById(VALID_POST_1.getId())).thenReturn(Optional.of(VALID_POST_1));

        doNothing().when(postRepository).deleteById(VALID_POST_1.getId());

        postService.deletePost(VALID_POST_1.getId());

        verify(postRepository, times(1)).findById(VALID_POST_1.getId());
        verify(postRepository, times(1)).deleteById(VALID_POST_1.getId());
    }

    @Test
    @DisplayName("Should throw PostNotFoundException when deleting non-existent post")
    void deletePost_ShouldThrowPostNotFoundException_WhenDeletingNonExistentPost() {
        when(postRepository.findById(INVALID_POST_ID)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> {
            postService.deletePost(INVALID_POST_ID);
        });

        verify(postRepository, times(1)).findById(INVALID_POST_ID);
        verify(postRepository, never()).deleteById(INVALID_POST_ID);
    }
}
