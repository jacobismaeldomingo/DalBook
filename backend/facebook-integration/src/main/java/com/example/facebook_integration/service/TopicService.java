package com.example.facebook_integration.service;

import com.example.facebook_integration.model.Topic;

import java.util.List;

public interface TopicService {
    Topic saveTopic(Topic topic);
    List<Topic> getAllTopics();
    Topic getLatestTopic();
}
