package com.example.facebook_integration.IntegrationTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.example.facebook_integration.controller.TopicController;
import com.example.facebook_integration.model.Topic;
import com.example.facebook_integration.service.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TopicController.class)
public class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController;

    private Topic topic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        topic = new Topic();
        topic.setId(1);
        topic.setTopic("Sample Topic");
    }

    @Test
    public void testCreateTopic() throws Exception {
        when(topicService.saveTopic(any(Topic.class))).thenReturn(topic);

        mockMvc.perform(post("/api/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(topic)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(topic)));
    }

    @Test
    public void testGetAllTopics() throws Exception {
        List<Topic> topics = Arrays.asList(topic);
        when(topicService.getAllTopics()).thenReturn(topics);

        mockMvc.perform(get("/api/topics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(topics)));
    }

    @Test
    public void testGetLatestTopic() throws Exception {
        when(topicService.getLatestTopic()).thenReturn(topic);

        mockMvc.perform(get("/api/topics/latest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(topic)));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
