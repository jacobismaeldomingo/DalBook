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

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestParam("text") String text,
                                           @RequestParam(value = "file", required = false) MultipartFile file,
                                           @RequestParam("feeling") String feeling) {
        Post post = new Post();
        post.setUserId(id);
        post.setDescription(text);
        post.setMediaUrl(feeling);

        if (file != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.write(path, bytes);
                post.setMediaUrl(path.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(postService.savePost(post));
    }
}
