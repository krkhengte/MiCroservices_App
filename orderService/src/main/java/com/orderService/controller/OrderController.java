package com.orderService.controller;


import com.orderService.client.PaymentClient;
import com.orderService.entity.OrderEntity;
import com.orderService.repo.OrderRepository;
import com.paymentService.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    NotificationClient notificationClient;

    @Autowired
    PaymentClient paymentClient;

//    @Autowired
//    private RestTemplate restTemplate;

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody OrderEntity order) {
        // Save the order
        OrderEntity savedOrder = orderRepository.save(order);

        // Call Payment Service
        Payment payment = new Payment();
        payment.setOrderId(savedOrder.getId());
        payment.setStatus("PENDING");
       Payment createdPayment = paymentClient.createPayment(payment);

//        String paymentServiceUrl = "http://PAYMENT-SERVICE/payments";
//        Payment paymentResponse = restTemplate.postForObject(paymentServiceUrl, payment, Payment.class);
//        Notification notification = new Notification();
//        notification.setMessage("hello");
//        notification.setId(1L);
//        notification.setOrderId(100L);
//        notificationClient.createNotification(notification);

        return "Order created: " + savedOrder.getId() + ", Payment Status: " + createdPayment.getStatus();
    }

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
