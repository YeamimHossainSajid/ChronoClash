package com.example.ContentService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    private UUID authorId;
    private String content;
    private List<String> mediaUrls = new ArrayList<>();

    // Temporal properties
    private LocalDateTime createdTimestamp;
    private LocalDateTime displayTimestamp;
    private LocalDateTime expiresAt;
    private String temporalContext; // "PAST", "PRESENT", "FUTURE"

    // Engagement
    private PostStats stats = new PostStats();
    private List<Comment> recentComments = new ArrayList<>();
    private Map<ReactionType, Integer> reactionCounts = new HashMap<>();

    // Paradox tracking
    private boolean isParadox;
    private Double paradoxScore;
    private String timelineBranchId;

    @DocumentReference(lazy = true)
    private List<Post> relatedPosts = new ArrayList<>();

    public Post() {
        this.createdTimestamp = LocalDateTime.now();
        this.displayTimestamp = LocalDateTime.now();
    }

    public Post(UUID authorId, String content) {
        this();
        this.authorId = authorId;
        this.content = content;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getDisplayTimestamp() {
        return displayTimestamp;
    }

    public void setDisplayTimestamp(LocalDateTime displayTimestamp) {
        this.displayTimestamp = displayTimestamp;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getTemporalContext() {
        return temporalContext;
    }

    public void setTemporalContext(String temporalContext) {
        this.temporalContext = temporalContext;
    }

    public PostStats getStats() {
        return stats;
    }

    public void setStats(PostStats stats) {
        this.stats = stats;
    }

    public List<Comment> getRecentComments() {
        return recentComments;
    }

    public void setRecentComments(List<Comment> recentComments) {
        this.recentComments = recentComments;
    }

    public Map<ReactionType, Integer> getReactionCounts() {
        return reactionCounts;
    }

    public void setReactionCounts(Map<ReactionType, Integer> reactionCounts) {
        this.reactionCounts = reactionCounts;
    }

    public boolean isParadox() {
        return isParadox;
    }

    public void setParadox(boolean paradox) {
        isParadox = paradox;
    }

    public Double getParadoxScore() {
        return paradoxScore;
    }

    public void setParadoxScore(Double paradoxScore) {
        this.paradoxScore = paradoxScore;
    }

    public String getTimelineBranchId() {
        return timelineBranchId;
    }

    public void setTimelineBranchId(String timelineBranchId) {
        this.timelineBranchId = timelineBranchId;
    }

    public List<Post> getRelatedPosts() {
        return relatedPosts;
    }

    public void setRelatedPosts(List<Post> relatedPosts) {
        this.relatedPosts = relatedPosts;
    }
}

