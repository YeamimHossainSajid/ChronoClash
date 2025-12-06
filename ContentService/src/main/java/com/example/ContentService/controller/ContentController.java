package com.example.ContentService.controller;

import com.example.ContentService.model.*;
import com.example.ContentService.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    // Post endpoints
    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request) {
        try {
            Post post = contentService.createPost(
                request.getAuthorId(), 
                request.getContent(), 
                request.getDisplayTimestamp());
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        return contentService.getPostById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/posts/author/{authorId}")
    public ResponseEntity<List<Post>> getPostsByAuthor(@PathVariable UUID authorId) {
        return ResponseEntity.ok(contentService.getPostsByAuthor(authorId));
    }

    @GetMapping("/posts/temporal/{context}")
    public ResponseEntity<List<Post>> getPostsByTemporalContext(@PathVariable String context) {
        return ResponseEntity.ok(contentService.getPostsByTemporalContext(context));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody UpdatePostRequest request) {
        try {
            Post post = contentService.updatePost(id, request.getContent());
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        contentService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // Comment endpoints
    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest request) {
        try {
            Comment comment = contentService.createComment(
                request.getPostId(),
                request.getAuthorId(),
                request.getContent(),
                request.getParentCommentId());
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable String postId) {
        return ResponseEntity.ok(contentService.getCommentsByPost(postId));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        try {
            contentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Reaction endpoints
    @PostMapping("/reactions")
    public ResponseEntity<Reaction> addReaction(@RequestBody CreateReactionRequest request) {
        try {
            Reaction reaction = contentService.addReaction(
                request.getTargetId(),
                request.getUserId(),
                request.getType());
            return ResponseEntity.ok(reaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/reactions")
    public ResponseEntity<Void> removeReaction(@RequestParam String targetId, @RequestParam UUID userId) {
        contentService.removeReaction(targetId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reactions/{targetId}")
    public ResponseEntity<List<Reaction>> getReactions(@PathVariable String targetId) {
        return ResponseEntity.ok(contentService.getReactionsByTarget(targetId));
    }

    // Time Capsule endpoints
    @PostMapping("/time-capsules")
    public ResponseEntity<TimeCapsule> createTimeCapsule(@RequestBody CreateTimeCapsuleRequest request) {
        try {
            TimeCapsule capsule = contentService.createTimeCapsule(
                request.getCreatorId(),
                request.getContent(),
                request.getUnlockTime(),
                request.getRecipientIds());
            return ResponseEntity.status(HttpStatus.CREATED).body(capsule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/time-capsules/creator/{creatorId}")
    public ResponseEntity<List<TimeCapsule>> getTimeCapsulesByCreator(@PathVariable UUID creatorId) {
        return ResponseEntity.ok(contentService.getTimeCapsulesByCreator(creatorId));
    }

    @GetMapping("/time-capsules/recipient/{recipientId}")
    public ResponseEntity<List<TimeCapsule>> getTimeCapsulesForRecipient(@PathVariable UUID recipientId) {
        return ResponseEntity.ok(contentService.getTimeCapsulesForRecipient(recipientId));
    }

    @PostMapping("/time-capsules/{id}/open")
    public ResponseEntity<TimeCapsule> openTimeCapsule(@PathVariable String id, @RequestParam UUID userId) {
        try {
            TimeCapsule capsule = contentService.openTimeCapsule(id, userId);
            return ResponseEntity.ok(capsule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/time-capsules/unlocked")
    public ResponseEntity<List<TimeCapsule>> getUnlockedTimeCapsules() {
        return ResponseEntity.ok(contentService.getUnlockedTimeCapsules());
    }

    // DTOs
    public static class CreatePostRequest {
        private UUID authorId;
        private String content;
        private LocalDateTime displayTimestamp;

        public UUID getAuthorId() { return authorId; }
        public void setAuthorId(UUID authorId) { this.authorId = authorId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public LocalDateTime getDisplayTimestamp() { return displayTimestamp; }
        public void setDisplayTimestamp(LocalDateTime displayTimestamp) { this.displayTimestamp = displayTimestamp; }
    }

    public static class UpdatePostRequest {
        private String content;

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    public static class CreateCommentRequest {
        private String postId;
        private UUID authorId;
        private String content;
        private String parentCommentId;

        public String getPostId() { return postId; }
        public void setPostId(String postId) { this.postId = postId; }
        public UUID getAuthorId() { return authorId; }
        public void setAuthorId(UUID authorId) { this.authorId = authorId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getParentCommentId() { return parentCommentId; }
        public void setParentCommentId(String parentCommentId) { this.parentCommentId = parentCommentId; }
    }

    public static class CreateReactionRequest {
        private String targetId;
        private UUID userId;
        private ReactionType type;

        public String getTargetId() { return targetId; }
        public void setTargetId(String targetId) { this.targetId = targetId; }
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        public ReactionType getType() { return type; }
        public void setType(ReactionType type) { this.type = type; }
    }

    public static class CreateTimeCapsuleRequest {
        private UUID creatorId;
        private String content;
        private LocalDateTime unlockTime;
        private List<UUID> recipientIds;

        public UUID getCreatorId() { return creatorId; }
        public void setCreatorId(UUID creatorId) { this.creatorId = creatorId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public LocalDateTime getUnlockTime() { return unlockTime; }
        public void setUnlockTime(LocalDateTime unlockTime) { this.unlockTime = unlockTime; }
        public List<UUID> getRecipientIds() { return recipientIds; }
        public void setRecipientIds(List<UUID> recipientIds) { this.recipientIds = recipientIds; }
    }
}

