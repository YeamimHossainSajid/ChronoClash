package com.example.TimelineService.repository;

import com.example.TimelineService.model.UserFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserFeedRepository extends JpaRepository<UserFeed, UUID> {
    Optional<UserFeed> findByUserId(UUID userId);
}

