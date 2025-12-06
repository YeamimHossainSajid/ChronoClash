package com.example.ParadoxEngine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "paradox_events")
public class ParadoxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParadoxType type;

    @ElementCollection
    @CollectionTable(name = "paradox_involved_entities", joinColumns = @JoinColumn(name = "paradox_id"))
    @Column(name = "entity_id")
    private List<String> involvedEntityIds = new ArrayList<>();

    @Column(name = "detected_at", nullable = false)
    private LocalDateTime detectedAt;

    @Column(nullable = false)
    private Double severity; // 0.0 to 1.0

    @Column(nullable = false)
    private Double confidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "resolution_status", nullable = false)
    private ResolutionStatus resolutionStatus = ResolutionStatus.DETECTED;

    @Column(name = "resolution_method")
    private String resolutionMethod; // "TIMELINE_SPLIT", "RETCON", "IGNORE"

    @Column(name = "timeline_branch_id")
    private String timelineBranchId;

    public ParadoxEvent() {
        this.detectedAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public ParadoxType getType() { return type; }
    public void setType(ParadoxType type) { this.type = type; }
    public List<String> getInvolvedEntityIds() { return involvedEntityIds; }
    public void setInvolvedEntityIds(List<String> involvedEntityIds) { this.involvedEntityIds = involvedEntityIds; }
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }
    public Double getSeverity() { return severity; }
    public void setSeverity(Double severity) { this.severity = severity; }
    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }
    public ResolutionStatus getResolutionStatus() { return resolutionStatus; }
    public void setResolutionStatus(ResolutionStatus resolutionStatus) { this.resolutionStatus = resolutionStatus; }
    public String getResolutionMethod() { return resolutionMethod; }
    public void setResolutionMethod(String resolutionMethod) { this.resolutionMethod = resolutionMethod; }
    public String getTimelineBranchId() { return timelineBranchId; }
    public void setTimelineBranchId(String timelineBranchId) { this.timelineBranchId = timelineBranchId; }
}

