package org.example.usersubscriptionservice.repository;

import org.example.usersubscriptionservice.entity.SubscriptionEntity;
import org.example.usersubscriptionservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
