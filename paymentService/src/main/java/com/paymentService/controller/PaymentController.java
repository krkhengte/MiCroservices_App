package com.paymentService.controller;

import com.notificationService.entity.Notification;
import com.paymentService.client.NotificationClient;
import com.paymentService.entity.Payment;
import com.paymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    NotificationClient notificationClient;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        // Process payment
        payment.setStatus("PAID");
        Payment savedPayment = paymentRepository.save(payment);

        // Call Notification Service
        Notification notification = new Notification();
        notification.setOrderId(savedPayment.getOrderId());
        notification.setMessage("Payment received for order: " + savedPayment.getOrderId());

        Notification savedNotification=notificationClient.createNotification(notification);

//        String notificationServiceUrl = "http://NOTIFICATION-SERVICE/notifications";
//        restTemplate.postForObject(notificationServiceUrl, notification, Notification.class);

        return savedPayment;
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
}