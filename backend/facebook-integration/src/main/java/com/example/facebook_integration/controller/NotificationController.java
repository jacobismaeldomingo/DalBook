package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.FriendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/send")
    public void sendFriendRequestNotification(@RequestBody FriendRequest friendRequest) {
        template.convertAndSend("/topic/notifications", friendRequest);
    }
}
