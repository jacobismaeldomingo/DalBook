package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.Post;
import com.example.facebook_integration.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestParam("text") String text) {
        Post post = new Post();
        post.setUserId(id);
        post.setDescription(text);

        return ResponseEntity.ok(postService.savePost(post));
    }
}
