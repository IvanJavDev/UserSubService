package org.example.usersubscriptionservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.usersubscriptionservice.entity.SubscriptionEntity;
import org.example.usersubscriptionservice.enums.ServiceType;
import org.example.usersubscriptionservice.enums.SubscriptionType;

@Data
@NoArgsConstructor
public class SubscriptionCreateDTO {
    private ServiceType serviceType;
    private SubscriptionType subscriptionType;
}
