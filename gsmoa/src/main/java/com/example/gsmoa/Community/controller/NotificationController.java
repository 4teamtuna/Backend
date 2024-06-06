package com.example.gsmoa.Community.controller;

import com.example.gsmoa.Community.entity.Notification;
import com.example.gsmoa.Community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/notification/read/{userId}/{notificationId}")
    public ResponseEntity<Void> checkNotification(@PathVariable Long userId, @PathVariable Long notificationId) {
        notificationService.checkNotification(userId, notificationId);
        return ResponseEntity.ok().build();
    }
}
