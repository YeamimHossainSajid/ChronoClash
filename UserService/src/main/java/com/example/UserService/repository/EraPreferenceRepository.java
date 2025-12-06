package com.example.UserService.repository;

import com.example.UserService.model.EraPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EraPreferenceRepository extends JpaRepository<EraPreference, UUID> {
    List<EraPreference> findByUserId(UUID userId);
    List<EraPreference> findByUserIdAndIsActiveTrue(UUID userId);
    Optional<EraPreference> findByUserIdAndEraName(UUID userId, String eraName);
}

