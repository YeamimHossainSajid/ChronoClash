package com.example.TimelineService.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "timeline_branches")
public class TimelineBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private UUID parentBranchId;

    @Column(name = "divergence_point")
    private LocalDateTime divergencePoint;

    @Column(name = "divergence_reason", columnDefinition = "TEXT")
    private String divergenceReason;

    @ElementCollection
    @CollectionTable(name = "branch_rules", joinColumns = @JoinColumn(name = "branch_id"))
    @MapKeyColumn(name = "rule_key")
    @Column(name = "rule_value")
    private Map<String, String> branchRules = new HashMap<>();

    @Column(name = "is_canon")
    private boolean isCanon = false;

    @Column(name = "user_count")
    private Integer userCount = 0;

    public TimelineBranch() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public UUID getParentBranchId() { return parentBranchId; }
    public void setParentBranchId(UUID parentBranchId) { this.parentBranchId = parentBranchId; }
    public LocalDateTime getDivergencePoint() { return divergencePoint; }
    public void setDivergencePoint(LocalDateTime divergencePoint) { this.divergencePoint = divergencePoint; }
    public String getDivergenceReason() { return divergenceReason; }
    public void setDivergenceReason(String divergenceReason) { this.divergenceReason = divergenceReason; }
    public Map<String, String> getBranchRules() { return branchRules; }
    public void setBranchRules(Map<String, String> branchRules) { this.branchRules = branchRules; }
    public boolean isCanon() { return isCanon; }
    public void setCanon(boolean canon) { isCanon = canon; }
    public Integer getUserCount() { return userCount; }
    public void setUserCount(Integer userCount) { this.userCount = userCount; }
}

