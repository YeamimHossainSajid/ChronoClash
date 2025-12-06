package com.example.UserService.repository;

import com.example.UserService.model.RelationshipStatus;
import com.example.UserService.model.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRelationshipRepository extends JpaRepository<UserRelationship, UUID> {
    Optional<UserRelationship> findByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
    List<UserRelationship> findByFollowerId(UUID followerId);
    List<UserRelationship> findByFollowingId(UUID followingId);
    List<UserRelationship> findByFollowerIdAndStatus(UUID followerId, RelationshipStatus status);
    List<UserRelationship> findByFollowingIdAndStatus(UUID followingId, RelationshipStatus status);
}

