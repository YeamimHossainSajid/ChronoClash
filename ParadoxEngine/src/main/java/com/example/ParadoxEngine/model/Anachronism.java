package com.example.ParadoxEngine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "anachronisms")
public class Anachronism {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "detected_anachronism", columnDefinition = "TEXT")
    private String detectedAnachronism;

    @Column(name = "entity_timestamp", nullable = false)
    private LocalDateTime entityTimestamp;

    @Column(name = "reference_timestamp", nullable = false)
    private LocalDateTime referenceTimestamp;

    @Column(name = "anachronism_score")
    private Double anachronismScore;

    @Column(name = "is_intentional")
    private boolean isIntentional = false;

    public Anachronism() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public String getDetectedAnachronism() { return detectedAnachronism; }
    public void setDetectedAnachronism(String detectedAnachronism) { this.detectedAnachronism = detectedAnachronism; }
    public LocalDateTime getEntityTimestamp() { return entityTimestamp; }
    public void setEntityTimestamp(LocalDateTime entityTimestamp) { this.entityTimestamp = entityTimestamp; }
    public LocalDateTime getReferenceTimestamp() { return referenceTimestamp; }
    public void setReferenceTimestamp(LocalDateTime referenceTimestamp) { this.referenceTimestamp = referenceTimestamp; }
    public Double getAnachronismScore() { return anachronismScore; }
    public void setAnachronismScore(Double anachronismScore) { this.anachronismScore = anachronismScore; }
    public boolean isIntentional() { return isIntentional; }
    public void setIntentional(boolean intentional) { isIntentional = intentional; }
}

