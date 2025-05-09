package org.example.usersubscriptionservice.repository;

import org.example.usersubscriptionservice.dto.SubscriptionStatsDTO;
import org.example.usersubscriptionservice.entity.SubscriptionEntity;
import org.example.usersubscriptionservice.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> findByUserId(Long userId);
    @Query("""
    SELECT NEW org.example.usersubscriptionservice.dto.SubscriptionStatsDTO(
        CAST(s.serviceType AS string),COUNT(s)
            )
    FROM SubscriptionEntity s 
    GROUP BY s.serviceType 
    ORDER BY COUNT(s) DESC 
    LIMIT 3""")
    List<SubscriptionStatsDTO> findTop3PopularSubscriptions();
    boolean existsByUserIdAndServiceTypeAndIsActiveTrue(Long userId, ServiceType serviceType);
}
