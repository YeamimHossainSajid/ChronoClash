package com.example.UserService.service;

import com.example.UserService.model.*;
import com.example.UserService.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRelationshipRepository relationshipRepository;

    @Autowired
    private UserTimelineEventRepository timelineEventRepository;

    @Autowired
    private EraPreferenceRepository eraPreferenceRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // User CRUD operations
    public User createUser(String username, String email, String passwordHash) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(username, email, passwordHash);
        user = userRepository.save(user);

        // Create timeline event
        UserTimelineEvent event = new UserTimelineEvent(user, EventType.JOINED, 
            "User joined ChronoClash", LocalDateTime.now());
        timelineEventRepository.save(event);

        // Publish event to Kafka
        kafkaTemplate.send("user-events", user.getId().toString(), user);

        return user;
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(UUID id, String username, String email) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (username != null && !username.equals(user.getUsername())) {
            if (userRepository.existsByUsername(username)) {
                throw new IllegalArgumentException("Username already exists");
            }
            user.setUsername(username);
        }

        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(email);
        }

        user.setVersion(user.getVersion() + 1);
        user = userRepository.save(user);

        // Create timeline event
        UserTimelineEvent event = new UserTimelineEvent(user, EventType.PROFILE_CHANGE, 
            "Profile updated", LocalDateTime.now());
        timelineEventRepository.save(event);

        kafkaTemplate.send("user-events", user.getId().toString(), user);

        return user;
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserTimelineEvent event = new UserTimelineEvent(user, EventType.ACCOUNT_DELETED, 
            "Account deleted", LocalDateTime.now());
        timelineEventRepository.save(event);

        userRepository.delete(user);
        kafkaTemplate.send("user-events", id.toString(), "DELETED");
    }

    // Relationship operations
    public UserRelationship followUser(UUID followerId, UUID followingId) {
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        User follower = userRepository.findById(followerId)
            .orElseThrow(() -> new IllegalArgumentException("Follower not found"));
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new IllegalArgumentException("User to follow not found"));

        Optional<UserRelationship> existing = relationshipRepository
            .findByFollowerIdAndFollowingId(followerId, followingId);

        if (existing.isPresent()) {
            UserRelationship rel = existing.get();
            if (rel.getStatus() == RelationshipStatus.ACTIVE) {
                throw new IllegalArgumentException("Already following this user");
            }
            rel.setStatus(RelationshipStatus.ACTIVE);
            rel.setFollowedAt(LocalDateTime.now());
            rel.setUnfollowedAt(null);
            return relationshipRepository.save(rel);
        }

        UserRelationship relationship = new UserRelationship(follower, following);
        relationship = relationshipRepository.save(relationship);

        // Create timeline events
        UserTimelineEvent followerEvent = new UserTimelineEvent(follower, EventType.FOLLOWED, 
            "Started following " + following.getUsername(), LocalDateTime.now());
        timelineEventRepository.save(followerEvent);

        kafkaTemplate.send("user-events", followerId.toString(), relationship);

        return relationship;
    }

    public void unfollowUser(UUID followerId, UUID followingId) {
        UserRelationship relationship = relationshipRepository
            .findByFollowerIdAndFollowingId(followerId, followingId)
            .orElseThrow(() -> new IllegalArgumentException("Relationship not found"));

        relationship.setStatus(RelationshipStatus.UNFOLLOWED);
        relationship.setUnfollowedAt(LocalDateTime.now());
        relationshipRepository.save(relationship);

        User follower = userRepository.findById(followerId).orElse(null);
        if (follower != null) {
            UserTimelineEvent event = new UserTimelineEvent(follower, EventType.UNFOLLOWED, 
                "Unfollowed user", LocalDateTime.now());
            timelineEventRepository.save(event);
        }

        kafkaTemplate.send("user-events", followerId.toString(), relationship);
    }

    public List<UserRelationship> getFollowing(UUID userId) {
        return relationshipRepository.findByFollowerIdAndStatus(userId, RelationshipStatus.ACTIVE);
    }

    public List<UserRelationship> getFollowers(UUID userId) {
        return relationshipRepository.findByFollowingIdAndStatus(userId, RelationshipStatus.ACTIVE);
    }

    // Timeline operations
    public List<UserTimelineEvent> getUserTimelineEvents(UUID userId) {
        return timelineEventRepository.findByUserIdOrderByEffectiveDateDesc(userId);
    }

    // Era preference operations
    public EraPreference addEraPreference(UUID userId, String eraName, String aestheticStyle) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        EraPreference preference = new EraPreference(user, eraName, aestheticStyle);
        return eraPreferenceRepository.save(preference);
    }

    public List<EraPreference> getUserEraPreferences(UUID userId) {
        return eraPreferenceRepository.findByUserIdAndIsActiveTrue(userId);
    }

    public void updateCurrentEra(UUID userId, String eraName) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getTemporalProfile() == null) {
            user.setTemporalProfile(new TemporalProfile());
        }

        user.getTemporalProfile().setCurrentEra(eraName);
        user.setVersion(user.getVersion() + 1);
        userRepository.save(user);

        UserTimelineEvent event = new UserTimelineEvent(user, EventType.ERA_SHIFT, 
            "Era shifted to " + eraName, LocalDateTime.now());
        timelineEventRepository.save(event);

        kafkaTemplate.send("user-events", userId.toString(), user);
    }
}

