package com.example.facebook_integration.IntegrationTests;

import com.example.facebook_integration.controller.PostController;
import com.example.facebook_integration.model.Post;
import com.example.facebook_integration.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private Post post;

    @BeforeEach
    public void setup() {
        post = new Post();
        post.setPostId(1);
        post.setUserId(1);
        post.setDescription("This is a test post");
        post.setMediaUrl("testfile.txt");
    }


    @Test
    public void testGetAllPosts() throws Exception {
        List<Post> posts = Arrays.asList(post);
        when(postService.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].postId").value(post.getPostId()))
                .andExpect(jsonPath("$[0].userId").value(post.getUserId()))
                .andExpect(jsonPath("$[0].description").value(post.getDescription()))
                .andExpect(jsonPath("$[0].mediaUrl").value(post.getMediaUrl()));

        Mockito.verify(postService, Mockito.times(1)).getAllPosts();
    }

    @Test
    public void testCreatePost() throws Exception {
        Path filePath = Paths.get("src/test/resources/testfile.txt");
        Files.createDirectories(filePath.getParent());
        Files.createFile(filePath);

        MockMultipartFile file = new MockMultipartFile("file", "testfile.txt", "text/plain", "This is a test file".getBytes());

        when(postService.savePost(any(Post.class))).thenReturn(post);

        mockMvc.perform(multipart("/api/posts/create/1")
                        .file(file)
                        .param("text", "This is a test post")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(post.getPostId()))
                .andExpect(jsonPath("$.userId").value(post.getUserId()))
                .andExpect(jsonPath("$.description").value(post.getDescription()))
                .andExpect(jsonPath("$.mediaUrl").value(post.getMediaUrl()));

        Mockito.verify(postService, Mockito.times(1)).savePost(any(Post.class));

        Files.deleteIfExists(filePath);
    }

    @Test
    public void testGetAllPosts_NoPostsFound() throws Exception {
        when(postService.getAllPosts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        Mockito.verify(postService, Mockito.times(1)).getAllPosts();
    }
}
