package com.example.TimelineService.service;

import com.example.TimelineService.model.*;
import com.example.TimelineService.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TimelineService {

    @Autowired
    private UserFeedRepository userFeedRepository;

    @Autowired
    private TimelineBranchRepository timelineBranchRepository;

    @Autowired
    private ChronoAlgorithmConfigRepository algorithmConfigRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public UserFeed getUserFeed(UUID userId) {
        Optional<UserFeed> feed = userFeedRepository.findByUserId(userId);
        if (feed.isEmpty()) {
            UserFeed newFeed = new UserFeed(userId);
            return userFeedRepository.save(newFeed);
        }
        return feed.get();
    }

    public UserFeed updateFeedAlgorithm(UUID userId, FeedAlgorithm algorithm) {
        UserFeed feed = getUserFeed(userId);
        feed.setAlgorithm(algorithm);
        feed.setLastUpdated(LocalDateTime.now());
        feed = userFeedRepository.save(feed);
        kafkaTemplate.send("feed-updates", userId.toString(), feed);
        return feed;
    }

    public ChronoAlgorithmConfig getAlgorithmConfig(UUID userId) {
        Optional<ChronoAlgorithmConfig> config = algorithmConfigRepository.findByUserId(userId);
        if (config.isEmpty()) {
            ChronoAlgorithmConfig newConfig = new ChronoAlgorithmConfig(userId);
            return algorithmConfigRepository.save(newConfig);
        }
        return config.get();
    }

    public ChronoAlgorithmConfig updateAlgorithmConfig(UUID userId, ChronoAlgorithmConfig config) {
        ChronoAlgorithmConfig existing = getAlgorithmConfig(userId);
        existing.setPastWeight(config.getPastWeight());
        existing.setFutureWeight(config.getFutureWeight());
        existing.setParadoxBoost(config.getParadoxBoost());
        existing.setNostalgiaFactor(config.getNostalgiaFactor());
        existing.setMaxTimeJumpDays(config.getMaxTimeJumpDays());
        existing.setAllowAnachronisms(config.isAllowAnachronisms());
        return algorithmConfigRepository.save(existing);
    }

    public TimelineBranch createTimelineBranch(String name, UUID parentBranchId, LocalDateTime divergencePoint, String reason) {
        TimelineBranch branch = new TimelineBranch();
        branch.setName(name);
        branch.setParentBranchId(parentBranchId);
        branch.setDivergencePoint(divergencePoint);
        branch.setDivergenceReason(reason);
        branch = timelineBranchRepository.save(branch);
        kafkaTemplate.send("timeline-events", branch.getId().toString(), branch);
        return branch;
    }

    public List<TimelineBranch> getAllBranches() {
        return timelineBranchRepository.findAll();
    }

    public void cacheTemporalCoherenceScore(UUID userId, Double score) {
        redisTemplate.opsForValue().set("temporal_coherence:" + userId, score, 1, TimeUnit.HOURS);
    }

    public Double getTemporalCoherenceScore(UUID userId) {
        Object score = redisTemplate.opsForValue().get("temporal_coherence:" + userId);
        return score != null ? (Double) score : 1.0;
    }
}

