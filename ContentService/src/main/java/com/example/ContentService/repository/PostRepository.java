package com.example.ContentService.repository;

import com.example.ContentService.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthorId(UUID authorId);
    List<Post> findByAuthorIdOrderByDisplayTimestampDesc(UUID authorId);
    List<Post> findByDisplayTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Post> findByTemporalContext(String temporalContext);
    List<Post> findByIsParadoxTrue();
    List<Post> findByTimelineBranchId(String timelineBranchId);
}

