package com.example.facebook_integration.repository;

import com.example.facebook_integration.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Topic findTopByOrderByIdDesc();
}

