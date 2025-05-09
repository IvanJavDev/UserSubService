package org.example.usersubscriptionservice.controller;

import org.example.usersubscriptionservice.dto.SubscriptionCreateDTO;
import org.example.usersubscriptionservice.dto.SubscriptionResponseDTO;
import org.example.usersubscriptionservice.dto.SubscriptionStatsDTO;
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
    public ResponseEntity<SubscriptionResponseDTO> addSubscription(
            @PathVariable Long userId,
            @RequestBody SubscriptionCreateDTO subscriptionCreateDTO) {
        SubscriptionResponseDTO createdSubscription = subscriptionService.addSubscription(userId, subscriptionCreateDTO);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponseDTO>> getUserSubscriptions(@PathVariable Long userId) {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService.getUserSubscriptions(userId);
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
    public ResponseEntity<List<SubscriptionStatsDTO>> getTop3PopularSubscriptions() {
        List<SubscriptionStatsDTO> topSubscriptions = subscriptionService.getTop3PopularSubscriptions();
        return ResponseEntity.ok(topSubscriptions);
    }
}