package com.example.UserService.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Embedded
    private TemporalProfile temporalProfile;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRelationship> following = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRelationship> followers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTimelineEvent> timelineEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EraPreference> eraPreferences = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Integer version = 1;

    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
        this.temporalProfile = new TemporalProfile();
    }

    public User(String username, String email, String passwordHash) {
        this();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public TemporalProfile getTemporalProfile() {
        return temporalProfile;
    }

    public void setTemporalProfile(TemporalProfile temporalProfile) {
        this.temporalProfile = temporalProfile;
    }

    public List<UserRelationship> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserRelationship> following) {
        this.following = following;
    }

    public List<UserRelationship> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserRelationship> followers) {
        this.followers = followers;
    }

    public List<UserTimelineEvent> getTimelineEvents() {
        return timelineEvents;
    }

    public void setTimelineEvents(List<UserTimelineEvent> timelineEvents) {
        this.timelineEvents = timelineEvents;
    }

    public List<EraPreference> getEraPreferences() {
        return eraPreferences;
    }

    public void setEraPreferences(List<EraPreference> eraPreferences) {
        this.eraPreferences = eraPreferences;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

