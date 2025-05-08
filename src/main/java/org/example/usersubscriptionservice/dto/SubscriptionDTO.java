package org.example.usersubscriptionservice.dto;

import lombok.Data;

@Data
public class SubscriptionDTO {
    private Long id;
    private String serviceName;
    private Long userId;
}
