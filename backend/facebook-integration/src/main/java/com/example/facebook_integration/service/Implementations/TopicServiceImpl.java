package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.Topic;
import com.example.facebook_integration.repository.TopicRepository;
import com.example.facebook_integration.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getLatestTopic() {
        return topicRepository.findTopByOrderByIdDesc();
    }
}
