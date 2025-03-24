package com.notificationService.controller;

import com.notificationService.entity.Notification;
import com.notificationService.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/createNotification")
    public Notification createNotification(@RequestBody Notification notification) {
        notification.setMessage("Payment received for order: " + notification.getOrderId());
        return notificationRepository.save(notification);
    }

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
}