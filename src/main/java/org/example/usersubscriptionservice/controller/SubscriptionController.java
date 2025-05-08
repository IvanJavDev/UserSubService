package org.example.usersubscriptionservice.controller;

import org.example.usersubscriptionservice.dto.SubscriptionDTO;
import org.example.usersubscriptionservice.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionDTO> addSubscription(
            @PathVariable Long userId,
            @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO createdSubscription = subscriptionService.addSubscription(userId, subscriptionDTO);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getUserSubscriptions(@PathVariable Long userId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }
}

@RestController
@RequestMapping("/subscriptions")
class TopSubscriptionsController {
    private final SubscriptionService subscriptionService;
    private static final Logger logger = LoggerFactory.getLogger(TopSubscriptionsController.class);

    public TopSubscriptionsController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/top")
    public ResponseEntity<List<Object[]>> getTop3PopularSubscriptions() {
        List<Object[]> topSubscriptions = subscriptionService.getTop3PopularSubscriptions();
        return ResponseEntity.ok(topSubscriptions);
    }
}