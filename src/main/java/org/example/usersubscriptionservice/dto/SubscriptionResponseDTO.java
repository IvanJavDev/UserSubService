package org.example.usersubscriptionservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Data
public class SubscriptionResponseDTO {
    private Long id;
    private String serviceName;
    private LocalDateTime expirationDate;
    private String subscriptionType;
    private Long userId;
}
