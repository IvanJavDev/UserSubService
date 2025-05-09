package org.example.usersubscriptionservice.service;

import org.example.usersubscriptionservice.dto.SubscriptionCreateDTO;
import org.example.usersubscriptionservice.dto.SubscriptionResponseDTO;
import org.example.usersubscriptionservice.dto.SubscriptionStatsDTO;
import org.example.usersubscriptionservice.entity.SubscriptionEntity;
import org.example.usersubscriptionservice.entity.UserEntity;
import org.example.usersubscriptionservice.enums.SubscriptionType;
import org.example.usersubscriptionservice.exceptions.BusinessValidationException;
import org.example.usersubscriptionservice.repository.SubscriptionRepository;
import org.example.usersubscriptionservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public SubscriptionResponseDTO addSubscription(Long userId, SubscriptionCreateDTO subscriptionCreateDTO) {
        logger.info("Adding subscription for user with id: {}", userId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean subscriptionExists = subscriptionRepository.existsByUserIdAndServiceTypeAndIsActiveTrue(
                userId,
                subscriptionCreateDTO.getServiceType()
        );

        if (subscriptionExists) {
            throw new BusinessValidationException(
                    "User already has active subscription for service: " + subscriptionCreateDTO.getServiceType()
            );
        }

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setServiceType(subscriptionCreateDTO.getServiceType());
        subscription.setSubscriptionType(subscriptionCreateDTO.getSubscriptionType());
        subscription.setExpirationDate(calculateExpirationDate(subscriptionCreateDTO.getSubscriptionType()));
        subscription.setUser(user);
        subscription.setIsActive(true);
        SubscriptionEntity savedSubscription = subscriptionRepository.save(subscription);
        return modelMapper.map(savedSubscription, SubscriptionResponseDTO.class);
    }

    private LocalDateTime calculateExpirationDate(SubscriptionType subscriptionType) {
        if (subscriptionType == null ) {
            throw new IllegalArgumentException("Subscription type cannot be null or empty");
        }

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC")); // Используем UTC для консистентности

        return switch (subscriptionType) {
            case MONTHLY -> now.plusMonths(1).withHour(23).withMinute(59).withSecond(59);
            case QUARTERLY -> now.plusMonths(3).withHour(23).withMinute(59).withSecond(59);
            case ANNUAL -> now.plusYears(1).withHour(23).withMinute(59).withSecond(59);
            case LIFETIME -> LocalDateTime.MAX;
            default -> throw new IllegalArgumentException(
                    String.format("Unknown subscription type: '%s'. Supported types: MONTHLY, QUARTERLY, ANNUAL, BIANNUAL, TRIAL, LIFETIME",
                            subscriptionType)
            );
        };
    }

    public List<SubscriptionResponseDTO> getUserSubscriptions(Long userId) {
        logger.info("Fetching subscriptions for user with id: {}", userId);
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findByUserId(userId);
        return subscriptions.stream()
                .map(sub -> modelMapper.map(sub, SubscriptionResponseDTO.class))
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

    public List<SubscriptionStatsDTO> getTop3PopularSubscriptions() {
        logger.info("Fetching top 3 popular subscriptions");
        return subscriptionRepository.findTop3PopularSubscriptions();
    }
}

