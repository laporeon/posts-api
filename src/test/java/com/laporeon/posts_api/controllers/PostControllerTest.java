package com.laporeon.posts_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laporeon.posts_api.dto.request.PostRequestDTO;
import com.laporeon.posts_api.exceptions.PostNotFoundException;
import com.laporeon.posts_api.services.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.laporeon.posts_api.commom.Constants.*;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @Test
    @DisplayName("Should return created status and saved post when given valid data")
    void createPost_ShouldReturnCreatedStatusAndSavedPost_WhenGivenValidData() throws Exception {
        when(postService.create(any(PostRequestDTO.class))).thenReturn(SAVED_POST_RESPONSE_DTO);

        mockMvc.perform(post("/posts")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(VALID_POST_REQUEST_DTO)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(SAVED_POST_RESPONSE_DTO.id()))
               .andExpect(jsonPath("$.title").value(SAVED_POST_RESPONSE_DTO.title()));
    }


    @Test
    @DisplayName("Should return validation errors when giving missing required fields")
    void createPost_ShouldReturnValidationErrors_WhenGivingMissingRequiredFields() throws Exception {
        mockMvc.perform(post("/posts")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(INVALID_POST_REQUEST_DTO)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.name()))
               .andExpect(jsonPath("$.messages").isArray())
               .andExpect(jsonPath("$.messages", hasItem("Title is required.")))
               .andExpect(jsonPath("$.messages", hasItem("Description is required.")))
               .andExpect(jsonPath("$.messages", hasItem("Body content is required.")));
    }

    @Test
    @DisplayName("Should return OK status and page of posts with full pagination info")
    void getAllPosts_ShouldReturnOKStatusAndPageOfPosts() throws Exception {
        when(postService.listPosts(any(Pageable.class))).thenReturn(POSTS_RESPONSE_PAGE);

        mockMvc.perform(get("/posts")
                       .param("page", "0")
                       .param("size", "10")
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content").isArray())
               .andExpect(jsonPath("$.totalElements").value(POSTS_ENTITY_LIST.size()))
               .andExpect(jsonPath("$.pageNumber").value(0))
               .andExpect(jsonPath("$.pageSize").value(POSTS_RESPONSE_PAGE.pageSize()))
               .andExpect(jsonPath("$.totalPages").value(POSTS_RESPONSE_PAGE.totalPages()))
               .andExpect(jsonPath("$.isSorted").value(true));
    }

    @Test
    @DisplayName("Should return OK status and existing Post")
    void findPostById_ShouldReturnOKStatusAndExistingPost() throws Exception {
        when(postService.findById(VALID_POST_ENTITY.getId())).thenReturn(SAVED_POST_RESPONSE_DTO);

        mockMvc.perform(get("/posts/{id}",VALID_POST_ENTITY.getId())
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(SAVED_POST_ENTITY.getId()))
               .andExpect(jsonPath("$.title").value(SAVED_POST_ENTITY.getTitle()));
    }

    @Test
    @DisplayName("Should return NOT FOUND status when given non existing id")
    void findPostById_ShouldReturnNotFoundStatus_WhenGiveNonExistingId() throws Exception {
        when(postService.findById(INVALID_POST_ID)).thenThrow(new PostNotFoundException(INVALID_POST_ID));

        mockMvc.perform(get("/posts/{id}", INVALID_POST_ID)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.name()));
    }

    @Test
    @DisplayName("Should return OK status when updating existing post with valid data")
    void updatePost_ShouldReturnOkStatus_WhenUpdatingExistingPost_GivenValidData() throws Exception {
        when(postService.update(eq(VALID_POST_ENTITY.getId()), any(PostRequestDTO.class)))
                .thenReturn(SAVED_POST_RESPONSE_DTO);

        mockMvc.perform(put("/posts/{id}", VALID_POST_ENTITY.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(VALID_POST_REQUEST_DTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value(SAVED_POST_RESPONSE_DTO.title()));
    }

    @Test
    @DisplayName("Should return not found status when updating non existing post")
    void updatePost_ShouldReturnNotFoundStatus_WhenUpdatingNonExistingPost() throws Exception {
        doThrow(new PostNotFoundException(INVALID_POST_ID))
                .when(postService)
                .update(INVALID_POST_ID, VALID_POST_REQUEST_DTO);

        mockMvc.perform(put("/posts/{id}", INVALID_POST_ID)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(VALID_POST_REQUEST_DTO)))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.name()));
    }

    @Test
    @DisplayName("Should return no content status when deleting existing post")
    void deletePost_ShouldReturnNoContentStatus_WhenDeletingExistingPost() throws Exception {
        doNothing().when(postService).delete(VALID_POST_ENTITY.getId());

        mockMvc.perform(delete("/posts/{id}", VALID_POST_ENTITY.getId())
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return not found status when deleting non existing post")
    void deletePost_ShouldReturnNotFoundStatus_WhenDeletingNonExistingPost() throws Exception {
        doThrow(new PostNotFoundException(INVALID_POST_ID))
                .when(postService)
                .delete(INVALID_POST_ID);

        mockMvc.perform(delete("/posts/{id}", INVALID_POST_ID)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.name()));
    }
}