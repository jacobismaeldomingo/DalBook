package com.example.facebook_integration.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.facebook_integration.model.Post;
import com.example.facebook_integration.repository.PostRepository;
import com.example.facebook_integration.service.Implementations.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post1;
    private Post post2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        post1 = new Post();
        post1.setPostId(1);
        post1.setUserId(1);
        post1.setDescription("This is a test post 1");

        post2 = new Post();
        post2.setPostId(2);
        post2.setUserId(2);
        post2.setDescription("This is a test post 2");
    }

    @Test
    public void testSavePost() {
        when(postRepository.save(post1)).thenReturn(post1);

        Post result = postService.savePost(post1);

        assertEquals(post1, result);
        verify(postRepository, times(1)).save(post1);
    }

    @Test
    public void testGetAllPosts() {
        List<Post> posts = Arrays.asList(post1, post2);
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();

        assertEquals(posts, result);
        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllPostsEmpty() {
        when(postRepository.findAll()).thenReturn(Collections.emptyList());

        List<Post> result = postService.getAllPosts();

        assertEquals(Collections.emptyList(), result);
        verify(postRepository, times(1)).findAll();
    }
}
