package com.example.ContentService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "time_capsules")
public class TimeCapsule {

    @Id
    private String id;

    private UUID creatorId;
    private List<UUID> recipientIds = new ArrayList<>();

    private String content;
    private LocalDateTime creationTime;
    private LocalDateTime unlockTime;
    private String unlockCondition; // "DATE", "EVENT", "LOCATION"

    private boolean isOpened;
    private LocalDateTime openedAt;

    public TimeCapsule() {
        this.creationTime = LocalDateTime.now();
        this.isOpened = false;
    }

    public TimeCapsule(UUID creatorId, String content, LocalDateTime unlockTime) {
        this();
        this.creatorId = creatorId;
        this.content = content;
        this.unlockTime = unlockTime;
        this.unlockCondition = "DATE";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public List<UUID> getRecipientIds() {
        return recipientIds;
    }

    public void setRecipientIds(List<UUID> recipientIds) {
        this.recipientIds = recipientIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(LocalDateTime unlockTime) {
        this.unlockTime = unlockTime;
    }

    public String getUnlockCondition() {
        return unlockCondition;
    }

    public void setUnlockCondition(String unlockCondition) {
        this.unlockCondition = unlockCondition;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }
}

