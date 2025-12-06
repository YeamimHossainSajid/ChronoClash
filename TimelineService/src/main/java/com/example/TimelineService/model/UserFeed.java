package com.example.TimelineService.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_feeds")
public class UserFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    @ElementCollection
    @CollectionTable(name = "feed_entries", joinColumns = @JoinColumn(name = "feed_id"))
    private List<FeedEntry> entries = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedAlgorithm algorithm = FeedAlgorithm.CHRONO;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public UserFeed() {
        this.lastUpdated = LocalDateTime.now();
    }

    public UserFeed(UUID userId) {
        this();
        this.userId = userId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public List<FeedEntry> getEntries() { return entries; }
    public void setEntries(List<FeedEntry> entries) { this.entries = entries; }
    public FeedAlgorithm getAlgorithm() { return algorithm; }
    public void setAlgorithm(FeedAlgorithm algorithm) { this.algorithm = algorithm; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}

