package com.example.TimelineService.controller;

import com.example.TimelineService.model.*;
import com.example.TimelineService.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/timeline")
public class TimelineController {

    @Autowired
    private TimelineService timelineService;

    @GetMapping("/feed/{userId}")
    public ResponseEntity<UserFeed> getUserFeed(@PathVariable UUID userId) {
        return ResponseEntity.ok(timelineService.getUserFeed(userId));
    }

    @PutMapping("/feed/{userId}/algorithm")
    public ResponseEntity<UserFeed> updateFeedAlgorithm(
            @PathVariable UUID userId,
            @RequestParam FeedAlgorithm algorithm) {
        return ResponseEntity.ok(timelineService.updateFeedAlgorithm(userId, algorithm));
    }

    @GetMapping("/algorithm-config/{userId}")
    public ResponseEntity<ChronoAlgorithmConfig> getAlgorithmConfig(@PathVariable UUID userId) {
        return ResponseEntity.ok(timelineService.getAlgorithmConfig(userId));
    }

    @PutMapping("/algorithm-config/{userId}")
    public ResponseEntity<ChronoAlgorithmConfig> updateAlgorithmConfig(
            @PathVariable UUID userId,
            @RequestBody ChronoAlgorithmConfig config) {
        return ResponseEntity.ok(timelineService.updateAlgorithmConfig(userId, config));
    }

    @PostMapping("/branches")
    public ResponseEntity<TimelineBranch> createTimelineBranch(@RequestBody CreateBranchRequest request) {
        TimelineBranch branch = timelineService.createTimelineBranch(
            request.getName(),
            request.getParentBranchId(),
            request.getDivergencePoint(),
            request.getDivergenceReason());
        return ResponseEntity.ok(branch);
    }

    @GetMapping("/branches")
    public ResponseEntity<List<TimelineBranch>> getAllBranches() {
        return ResponseEntity.ok(timelineService.getAllBranches());
    }

    @GetMapping("/coherence/{userId}")
    public ResponseEntity<Double> getTemporalCoherenceScore(@PathVariable UUID userId) {
        return ResponseEntity.ok(timelineService.getTemporalCoherenceScore(userId));
    }

    public static class CreateBranchRequest {
        private String name;
        private UUID parentBranchId;
        private LocalDateTime divergencePoint;
        private String divergenceReason;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public UUID getParentBranchId() { return parentBranchId; }
        public void setParentBranchId(UUID parentBranchId) { this.parentBranchId = parentBranchId; }
        public LocalDateTime getDivergencePoint() { return divergencePoint; }
        public void setDivergencePoint(LocalDateTime divergencePoint) { this.divergencePoint = divergencePoint; }
        public String getDivergenceReason() { return divergenceReason; }
        public void setDivergenceReason(String divergenceReason) { this.divergenceReason = divergenceReason; }
    }
}

