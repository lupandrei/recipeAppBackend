package com.spring.recipeapp.service;

import com.fasterxml.jackson.core.JsonToken;
import com.spring.recipeapp.dto.notification.RecipeAddNotification;
import com.spring.recipeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    private UserRepository userRepository;
    private Map<String, List<String>> activeUsersWithFollowers = new HashMap<>();

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void addUser(String userId) {
        activeUsersWithFollowers.putIfAbsent(userId, new ArrayList<>());
        for (Map.Entry<String, List<String>> entry : activeUsersWithFollowers.entrySet()) {
            if(userRepository.isFollowing(entry.getKey(),userId)){
                activeUsersWithFollowers.get(userId).add(entry.getKey());
                System.out.println("user is followed by existent");
            }

            if(userRepository.isFollowing(userId,entry.getKey()))
            {
                entry.getValue().add(userId);
                System.out.println("user is following existent");
            }
        }
//        // Check if the new user is followed by existing users
//        activeUsersWithFollowers.forEach((existingUserId, followers) -> {
//            if (!existingUserId.equals(userId) && !followers.contains(userId)) {
//                // Assuming you have a method to check if a user follows another
//                if (userRepository.isFollowing(userId,existingUserId)) {
//                    System.out.println("new user is followed by existing user");
//                    followers.add(userId);
//                }
//            }
//        });
//        // Check if the new user follows existing users
//        activeUsersWithFollowers.get(userId).forEach(existingUserId -> {
//            if (!existingUserId.equals(userId) && !activeUsersWithFollowers.get(existingUserId).contains(userId)) {
//                // Assuming you have a method to check if a user follows another
//                if (userRepository.isFollowing(existingUserId,userId)) {
//                    System.out.println("new user follows existing user");
//                    activeUsersWithFollowers.get(existingUserId).add(userId);
//                }
//            }
//        });
    }
    public void removeUser(String userId) {
        // Remove the user from the map
        activeUsersWithFollowers.remove(userId);

        // Remove the user from the follower lists of other users
        activeUsersWithFollowers.forEach((existingUserId, followers) -> {
            followers.remove(userId);
        });
    }
    public List<String> getFollowers(String userId) {
        System.out.println(userId);
        return activeUsersWithFollowers.get(userId);
    }

    // Notify followers about a new recipe
    public void notifyFollowers(String userId, String destination, RecipeAddNotification notification) {
        List<String> followers = getFollowers(userId);
        System.out.println(followers);
        for (String followerId : followers) {
            System.out.println(followerId + " "+ destination + " " +notification);
            sendWebSocketMessage(followerId, destination, notification);
        }
    }

    private void sendWebSocketMessage(String userId, String destination, RecipeAddNotification notification) {
        messagingTemplate.convertAndSend(destination+"/"+userId, notification);
    }
    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();

    }
}
