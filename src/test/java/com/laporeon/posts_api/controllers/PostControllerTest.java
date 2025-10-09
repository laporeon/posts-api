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
    @DisplayName("Should return 201 and saved post when given valid request data")
    void create_WithValidRequestData_ReturnsCreatedAndPost() throws Exception {
        when(postService.create(any(PostRequestDTO.class))).thenReturn(SAVED_POST_RESPONSE_DTO);

        mockMvc.perform(post("/posts")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(VALID_POST_REQUEST_DTO)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(SAVED_POST_RESPONSE_DTO.id()))
               .andExpect(jsonPath("$.title").value(SAVED_POST_RESPONSE_DTO.title()));
    }


    @Test
    @DisplayName("Should return 400 when required fields are missing or invalid")
    void create_WithMissingOrInvalidFields_ReturnsBadRequest() throws Exception {
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
    @DisplayName("Should return 200 and paginated posts")
    void listPosts_ReturnsOkAndPagedPosts() throws Exception {
        when(postService.listPosts(any(Pageable.class))).thenReturn(POSTS_RESPONSE_PAGE);

        mockMvc.perform(get("/posts")
                       .param("page", "0")
                       .param("size", "10")
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content").isArray())
               .andExpect(jsonPath("$.content").isNotEmpty())
               .andExpect(jsonPath("$.totalElements").value(POSTS_RESPONSE_PAGE.totalElements()))
               .andExpect(jsonPath("$.pageNumber").value(0))
               .andExpect(jsonPath("$.pageSize").value(POSTS_RESPONSE_PAGE.pageSize()))
               .andExpect(jsonPath("$.totalPages").value(POSTS_RESPONSE_PAGE.totalPages()))
               .andExpect(jsonPath("$.isSorted").value(true));
    }

    @Test
    @DisplayName("Should return 200 and post when id exists")
    void findPostById_WithExistingId_ReturnsOkAndPost() throws Exception {
        when(postService.findById(VALID_POST_ENTITY.getId())).thenReturn(SAVED_POST_RESPONSE_DTO);

        mockMvc.perform(get("/posts/{id}",VALID_POST_ENTITY.getId())
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(SAVED_POST_RESPONSE_DTO.id()))
               .andExpect(jsonPath("$.title").value(SAVED_POST_RESPONSE_DTO.title()));
    }

    @Test
    @DisplayName("Should return 404 when finding post with non existing id")
    void findPostById_WithNonExistingId_ReturnsNotFound() throws Exception {
        when(postService.findById(INVALID_POST_ID)).thenThrow(new PostNotFoundException(INVALID_POST_ID));

        mockMvc.perform(get("/posts/{id}", INVALID_POST_ID))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.name()));
    }

    @Test
    @DisplayName("Should return 200 when updating post with existing id and valid request data")
    void update_WithExistingIdAndValidRequestData_ReturnsOkAndPost() throws Exception {
        when(postService.update(eq(VALID_POST_ENTITY.getId()), any(PostRequestDTO.class)))
                .thenReturn(SAVED_POST_RESPONSE_DTO);

        mockMvc.perform(put("/posts/{id}", VALID_POST_ENTITY.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(VALID_POST_REQUEST_DTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value(SAVED_POST_RESPONSE_DTO.title()));
    }

    @Test
    @DisplayName("Should return 404 when updating post with non existing id")
    void update_WithNonExistingId_ReturnsNotFound() throws Exception {
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
    @DisplayName("Should return 204 when deleting post with existing id")
    void delete_WithExistingId_ReturnsNoContent() throws Exception {
        doNothing().when(postService).delete(VALID_POST_ENTITY.getId());

        mockMvc.perform(delete("/posts/{id}", VALID_POST_ENTITY.getId()))
               .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 when deleting post with non existing id")
    void delete_WithNonExistingId_ReturnsNotFound() throws Exception {
        doThrow(new PostNotFoundException(INVALID_POST_ID))
                .when(postService)
                .delete(INVALID_POST_ID);

        mockMvc.perform(delete("/posts/{id}", INVALID_POST_ID))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.name()));
    }
}