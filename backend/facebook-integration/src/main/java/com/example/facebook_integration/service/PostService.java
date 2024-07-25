package com.example.facebook_integration.service;

import com.example.facebook_integration.model.Post;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post savePost(Post post);
}
