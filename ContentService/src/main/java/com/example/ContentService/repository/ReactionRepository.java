package com.example.ContentService.repository;

import com.example.ContentService.model.Reaction;
import com.example.ContentService.model.ReactionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction, String> {
    List<Reaction> findByTargetId(String targetId);
    Optional<Reaction> findByTargetIdAndUserId(String targetId, UUID userId);
    List<Reaction> findByUserId(UUID userId);
    List<Reaction> findByTargetIdAndType(String targetId, ReactionType type);
}

