package com.example.ContentService.service;

import com.example.ContentService.model.*;
import com.example.ContentService.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private TimeCapsuleRepository timeCapsuleRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // Post operations
    public Post createPost(UUID authorId, String content, LocalDateTime displayTimestamp) {
        Post post = new Post(authorId, content);
        post.setDisplayTimestamp(displayTimestamp != null ? displayTimestamp : LocalDateTime.now());
        
        // Determine temporal context
        LocalDateTime now = LocalDateTime.now();
        if (post.getDisplayTimestamp().isBefore(now.minusDays(1))) {
            post.setTemporalContext("PAST");
        } else if (post.getDisplayTimestamp().isAfter(now.plusDays(1))) {
            post.setTemporalContext("FUTURE");
        } else {
            post.setTemporalContext("PRESENT");
        }

        post = postRepository.save(post);
        kafkaTemplate.send("content-events", post.getId(), post);
        return post;
    }

    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }

    public List<Post> getPostsByAuthor(UUID authorId) {
        return postRepository.findByAuthorIdOrderByDisplayTimestampDesc(authorId);
    }

    public List<Post> getPostsByTemporalContext(String temporalContext) {
        return postRepository.findByTemporalContext(temporalContext);
    }

    public Post updatePost(String id, String content) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.setContent(content);
        post = postRepository.save(post);
        kafkaTemplate.send("content-events", post.getId(), post);
        return post;
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
        kafkaTemplate.send("content-events", id, "DELETED");
    }

    // Comment operations
    public Comment createComment(String postId, UUID authorId, String content, String parentCommentId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = new Comment(postId, authorId, content);
        comment.setTargetPostTime(post.getDisplayTimestamp());
        
        if (parentCommentId != null) {
            comment.setParentCommentId(parentCommentId);
            Comment parent = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
            comment.setDepth(parent.getDepth() + 1);
        }

        // Determine temporal relation
        LocalDateTime now = LocalDateTime.now();
        if (comment.getCommentTime().isBefore(post.getDisplayTimestamp())) {
            comment.setTemporalRelation("BEFORE");
        } else if (comment.getCommentTime().isAfter(post.getDisplayTimestamp().plusHours(1))) {
            comment.setTemporalRelation("AFTER");
        } else {
            comment.setTemporalRelation("SIMULTANEOUS");
        }

        comment = commentRepository.save(comment);
        
        // Update post stats
        post.getStats().setCommentCount(post.getStats().getCommentCount() + 1);
        postRepository.save(post);

        kafkaTemplate.send("content-events", comment.getId(), comment);
        return comment;
    }

    public List<Comment> getCommentsByPost(String postId) {
        return commentRepository.findByPostIdOrderByCommentTimeAsc(postId);
    }

    public void deleteComment(String id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        
        Post post = postRepository.findById(comment.getPostId()).orElse(null);
        if (post != null) {
            post.getStats().setCommentCount(Math.max(0, post.getStats().getCommentCount() - 1));
            postRepository.save(post);
        }

        commentRepository.deleteById(id);
        kafkaTemplate.send("content-events", id, "DELETED");
    }

    // Reaction operations
    public Reaction addReaction(String targetId, UUID userId, ReactionType type) {
        Optional<Reaction> existing = reactionRepository.findByTargetIdAndUserId(targetId, userId);
        
        if (existing.isPresent()) {
            Reaction reaction = existing.get();
            reaction.setType(type);
            reaction.setReactionTime(LocalDateTime.now());
            return reactionRepository.save(reaction);
        }

        Reaction reaction = new Reaction(targetId, userId, type);
        reaction = reactionRepository.save(reaction);

        // Update post or comment reaction counts
        Optional<Post> post = postRepository.findById(targetId);
        if (post.isPresent()) {
            Post p = post.get();
            p.getReactionCounts().put(type, p.getReactionCounts().getOrDefault(type, 0) + 1);
            p.getStats().setReactionCount(p.getStats().getReactionCount() + 1);
            postRepository.save(p);
        }

        kafkaTemplate.send("content-events", reaction.getId(), reaction);
        return reaction;
    }

    public void removeReaction(String targetId, UUID userId) {
        Optional<Reaction> reaction = reactionRepository.findByTargetIdAndUserId(targetId, userId);
        if (reaction.isPresent()) {
            ReactionType type = reaction.get().getType();
            reactionRepository.delete(reaction.get());

            // Update post reaction counts
            Optional<Post> post = postRepository.findById(targetId);
            if (post.isPresent()) {
                Post p = post.get();
                p.getReactionCounts().put(type, Math.max(0, p.getReactionCounts().getOrDefault(type, 0) - 1));
                p.getStats().setReactionCount(Math.max(0, p.getStats().getReactionCount() - 1));
                postRepository.save(p);
            }
        }
    }

    public List<Reaction> getReactionsByTarget(String targetId) {
        return reactionRepository.findByTargetId(targetId);
    }

    // Time Capsule operations
    public TimeCapsule createTimeCapsule(UUID creatorId, String content, LocalDateTime unlockTime, List<UUID> recipientIds) {
        TimeCapsule capsule = new TimeCapsule(creatorId, content, unlockTime);
        if (recipientIds != null) {
            capsule.setRecipientIds(recipientIds);
        }
        capsule = timeCapsuleRepository.save(capsule);
        kafkaTemplate.send("content-events", capsule.getId(), capsule);
        return capsule;
    }

    public List<TimeCapsule> getTimeCapsulesByCreator(UUID creatorId) {
        return timeCapsuleRepository.findByCreatorId(creatorId);
    }

    public List<TimeCapsule> getTimeCapsulesForRecipient(UUID recipientId) {
        return timeCapsuleRepository.findByRecipientIdsContaining(recipientId);
    }

    public TimeCapsule openTimeCapsule(String id, UUID userId) {
        TimeCapsule capsule = timeCapsuleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Time capsule not found"));

        if (!capsule.getCreatorId().equals(userId) && 
            !capsule.getRecipientIds().contains(userId)) {
            throw new IllegalArgumentException("Not authorized to open this time capsule");
        }

        if (LocalDateTime.now().isBefore(capsule.getUnlockTime())) {
            throw new IllegalArgumentException("Time capsule is not yet unlocked");
        }

        capsule.setOpened(true);
        capsule.setOpenedAt(LocalDateTime.now());
        capsule = timeCapsuleRepository.save(capsule);
        
        kafkaTemplate.send("content-events", capsule.getId(), capsule);
        return capsule;
    }

    public List<TimeCapsule> getUnlockedTimeCapsules() {
        return timeCapsuleRepository.findByUnlockTimeBeforeAndIsOpenedFalse(LocalDateTime.now());
    }
}

