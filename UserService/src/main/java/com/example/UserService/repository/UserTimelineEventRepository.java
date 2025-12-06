package com.example.UserService.repository;

import com.example.UserService.model.EventType;
import com.example.UserService.model.UserTimelineEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserTimelineEventRepository extends JpaRepository<UserTimelineEvent, UUID> {
    List<UserTimelineEvent> findByUserIdOrderByEffectiveDateDesc(UUID userId);
    List<UserTimelineEvent> findByUserIdAndType(UUID userId, EventType type);
    List<UserTimelineEvent> findByUserIdAndEffectiveDateBetween(UUID userId, LocalDateTime start, LocalDateTime end);
}

