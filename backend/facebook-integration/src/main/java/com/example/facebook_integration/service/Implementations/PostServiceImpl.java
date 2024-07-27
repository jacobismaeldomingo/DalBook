package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.Post;
import com.example.facebook_integration.repository.PostRepository;
import com.example.facebook_integration.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post savePost(Post post) {
        post.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return postRepository.save(post);
    }
}
