package com.example.ContentService.repository;

import com.example.ContentService.model.TimeCapsule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeCapsuleRepository extends MongoRepository<TimeCapsule, String> {
    List<TimeCapsule> findByCreatorId(UUID creatorId);
    List<TimeCapsule> findByRecipientIdsContaining(UUID recipientId);
    List<TimeCapsule> findByUnlockTimeBeforeAndIsOpenedFalse(LocalDateTime now);
    List<TimeCapsule> findByCreatorIdAndIsOpenedFalse(UUID creatorId);
}

