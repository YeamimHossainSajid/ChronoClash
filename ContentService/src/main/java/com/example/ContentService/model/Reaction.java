package com.example.ContentService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "reactions")
public class Reaction {

    @Id
    private String id;

    private String targetId; // Post or Comment ID
    private UUID userId;
    private ReactionType type;

    // Temporal context
    private LocalDateTime reactionTime;
    private LocalDateTime targetCreationTime;
    private String eraContext; // "retro_like", "future_heart"

    public Reaction() {
        this.reactionTime = LocalDateTime.now();
    }

    public Reaction(String targetId, UUID userId, ReactionType type) {
        this();
        this.targetId = targetId;
        this.userId = userId;
        this.type = type;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }

    public LocalDateTime getReactionTime() {
        return reactionTime;
    }

    public void setReactionTime(LocalDateTime reactionTime) {
        this.reactionTime = reactionTime;
    }

    public LocalDateTime getTargetCreationTime() {
        return targetCreationTime;
    }

    public void setTargetCreationTime(LocalDateTime targetCreationTime) {
        this.targetCreationTime = targetCreationTime;
    }

    public String getEraContext() {
        return eraContext;
    }

    public void setEraContext(String eraContext) {
        this.eraContext = eraContext;
    }
}

