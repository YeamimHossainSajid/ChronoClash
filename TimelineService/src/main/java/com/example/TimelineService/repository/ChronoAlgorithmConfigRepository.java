package com.example.TimelineService.repository;

import com.example.TimelineService.model.ChronoAlgorithmConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChronoAlgorithmConfigRepository extends JpaRepository<ChronoAlgorithmConfig, UUID> {
    Optional<ChronoAlgorithmConfig> findByUserId(UUID userId);
}

