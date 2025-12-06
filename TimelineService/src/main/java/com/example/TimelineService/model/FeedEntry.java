package com.example.TimelineService.model;

import java.time.LocalDateTime;

public class FeedEntry {
    private String postId;
    private LocalDateTime displayTime;
    private Double relevanceScore;
    private String timelineContext;
    private String eraTag; // "throwback", "current", "preview"

    public FeedEntry() {
    }

    public FeedEntry(String postId, LocalDateTime displayTime) {
        this.postId = postId;
        this.displayTime = displayTime;
        this.relevanceScore = 1.0;
    }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }
    public LocalDateTime getDisplayTime() { return displayTime; }
    public void setDisplayTime(LocalDateTime displayTime) { this.displayTime = displayTime; }
    public Double getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(Double relevanceScore) { this.relevanceScore = relevanceScore; }
    public String getTimelineContext() { return timelineContext; }
    public void setTimelineContext(String timelineContext) { this.timelineContext = timelineContext; }
    public String getEraTag() { return eraTag; }
    public void setEraTag(String eraTag) { this.eraTag = eraTag; }
}

