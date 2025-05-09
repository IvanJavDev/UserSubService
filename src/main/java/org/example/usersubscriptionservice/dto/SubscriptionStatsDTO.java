package org.example.usersubscriptionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubscriptionStatsDTO {
    private String serviceName;
    private Long subscriptionCount;

}
