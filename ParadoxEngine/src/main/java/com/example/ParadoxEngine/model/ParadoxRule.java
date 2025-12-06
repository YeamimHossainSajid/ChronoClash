package com.example.ParadoxEngine.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "paradox_rules")
public class ParadoxRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "rule_name", nullable = false, unique = true)
    private String ruleName;

    @Column(columnDefinition = "TEXT")
    private String condition;

    @Column(nullable = false)
    private String action; // "PREVENT", "ALLOW_WARNING", "CREATE_BRANCH"

    @Column(nullable = false)
    private Integer priority = 0;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public ParadoxRule() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}

