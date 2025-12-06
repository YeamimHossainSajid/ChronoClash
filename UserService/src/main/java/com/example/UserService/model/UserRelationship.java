package com.example.UserService.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_relationships")
public class UserRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RelationshipStatus status = RelationshipStatus.ACTIVE;

    @Column(name = "followed_at")
    private LocalDateTime followedAt;

    @Column(name = "unfollowed_at")
    private LocalDateTime unfollowedAt;

    public UserRelationship() {
        this.followedAt = LocalDateTime.now();
    }

    public UserRelationship(User follower, User following) {
        this();
        this.follower = follower;
        this.following = following;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    public RelationshipStatus getStatus() {
        return status;
    }

    public void setStatus(RelationshipStatus status) {
        this.status = status;
    }

    public LocalDateTime getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(LocalDateTime followedAt) {
        this.followedAt = followedAt;
    }

    public LocalDateTime getUnfollowedAt() {
        return unfollowedAt;
    }

    public void setUnfollowedAt(LocalDateTime unfollowedAt) {
        this.unfollowedAt = unfollowedAt;
    }
}

