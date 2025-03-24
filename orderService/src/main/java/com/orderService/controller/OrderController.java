package com.orderService.controller;

import com.orderService.entity.OrderEntity;
import com.orderService.repo.OrderRepository;
import com.paymentService.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public String createOrder(@RequestBody OrderEntity order) {
        // Save the order
        OrderEntity savedOrder = orderRepository.save(order);

        // Call Payment Service
        Payment payment = new Payment();
        payment.setOrderId(savedOrder.getId());
        payment.setStatus("PENDING");

        String paymentServiceUrl = "http://PAYMENT-SERVICE/payments";
        Payment paymentResponse = restTemplate.postForObject(paymentServiceUrl, payment, Payment.class);

        return "Order created: " + savedOrder.getId() + ", Payment Status: " + paymentResponse.getStatus();
    }

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
