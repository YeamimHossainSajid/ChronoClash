package com.example.ParadoxEngine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "timeline_consistency_checks")
public class TimelineConsistencyCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "check_type", nullable = false)
    private String checkType; // "CAUSALITY", "CHRONOLOGY", "ENTITY_INTEGRITY"

    @Column(name = "check_time", nullable = false)
    private LocalDateTime checkTime;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String violations; // JSON array of violations

    @Column(nullable = false)
    private boolean passed;

    @Column(name = "corrective_action", columnDefinition = "TEXT")
    private String correctiveAction;

    public TimelineConsistencyCheck() {
        this.checkTime = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCheckType() { return checkType; }
    public void setCheckType(String checkType) { this.checkType = checkType; }
    public LocalDateTime getCheckTime() { return checkTime; }
    public void setCheckTime(LocalDateTime checkTime) { this.checkTime = checkTime; }
    public String getViolations() { return violations; }
    public void setViolations(String violations) { this.violations = violations; }
    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
    public String getCorrectiveAction() { return correctiveAction; }
    public void setCorrectiveAction(String correctiveAction) { this.correctiveAction = correctiveAction; }
}

