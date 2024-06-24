package com.example.carrotdiary.follow.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    public void sendFollowNotification(String topicArn, String follower, String followee) {
        String message = String.format("%s님이 당신을 팔로잉 합니다", followee);

        notificationMessagingTemplate.sendNotification(topicArn, message,"팔로우 알림");

    }

}
