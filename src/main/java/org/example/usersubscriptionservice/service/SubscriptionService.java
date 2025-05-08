package org.example.usersubscriptionservice.service;

import org.example.usersubscriptionservice.dto.SubscriptionDTO;
import org.example.usersubscriptionservice.entity.SubscriptionEntity;
import org.example.usersubscriptionservice.entity.UserEntity;
import org.example.usersubscriptionservice.repository.SubscriptionRepository;
import org.example.usersubscriptionservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserRepository userRepository,
                               ModelMapper modelMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public SubscriptionDTO addSubscription(Long userId, SubscriptionDTO subscriptionDTO) {
        logger.info("Adding subscription for user with id: {}", userId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SubscriptionEntity subscription = modelMapper.map(subscriptionDTO, SubscriptionEntity.class);
        subscription.setUser(user);
        SubscriptionEntity savedSubscription = subscriptionRepository.save(subscription);
        return modelMapper.map(savedSubscription, SubscriptionDTO.class);
    }

    public List<SubscriptionDTO> getUserSubscriptions(Long userId) {
        logger.info("Fetching subscriptions for user with id: {}", userId);
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findByUserId(userId);
        return subscriptions.stream()
                .map(sub -> modelMapper.map(sub, SubscriptionDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        logger.info("Deleting subscription with id: {} for user with id: {}", subscriptionId, userId);
        SubscriptionEntity subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new RuntimeException("Subscription does not belong to the user");
        }

        subscriptionRepository.delete(subscription);
    }

    public List<Object[]> getTop3PopularSubscriptions() {
        logger.info("Fetching top 3 popular subscriptions");
        return subscriptionRepository.findTop3PopularSubscriptions();
    }
}

