package com.example.ContentService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String postId;
    private UUID authorId;
    private String content;

    // Temporal properties
    private LocalDateTime commentTime;
    private LocalDateTime targetPostTime;
    private String temporalRelation; // "BEFORE", "AFTER", "SIMULTANEOUS"

    // Threading
    private String parentCommentId;
    private Integer depth = 0;

    private List<Reaction> reactions = new ArrayList<>();

    public Comment() {
        this.commentTime = LocalDateTime.now();
    }

    public Comment(String postId, UUID authorId, String content) {
        this();
        this.postId = postId;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public LocalDateTime getTargetPostTime() {
        return targetPostTime;
    }

    public void setTargetPostTime(LocalDateTime targetPostTime) {
        this.targetPostTime = targetPostTime;
    }

    public String getTemporalRelation() {
        return temporalRelation;
    }

    public void setTemporalRelation(String temporalRelation) {
        this.temporalRelation = temporalRelation;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }
}

