package com.example.ContentService.model;

import java.util.Map;

public class PostStats {
    private Integer viewCount;
    private Integer commentCount;
    private Integer reactionCount;
    private Map<ReactionType, Integer> reactionBreakdown;
    private Double engagementRate;

    public PostStats() {
        this.viewCount = 0;
        this.commentCount = 0;
        this.reactionCount = 0;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getReactionCount() {
        return reactionCount;
    }

    public void setReactionCount(Integer reactionCount) {
        this.reactionCount = reactionCount;
    }

    public Map<ReactionType, Integer> getReactionBreakdown() {
        return reactionBreakdown;
    }

    public void setReactionBreakdown(Map<ReactionType, Integer> reactionBreakdown) {
        this.reactionBreakdown = reactionBreakdown;
    }

    public Double getEngagementRate() {
        return engagementRate;
    }

    public void setEngagementRate(Double engagementRate) {
        this.engagementRate = engagementRate;
    }
}

