package com.example.ContentService.repository;

import com.example.ContentService.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId);
    List<Comment> findByPostIdOrderByCommentTimeAsc(String postId);
    List<Comment> findByAuthorId(UUID authorId);
    List<Comment> findByParentCommentId(String parentCommentId);
}

