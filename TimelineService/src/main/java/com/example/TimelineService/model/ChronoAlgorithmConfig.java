package com.example.TimelineService.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "chrono_algorithm_configs")
public class ChronoAlgorithmConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    @Column(name = "past_weight")
    private Double pastWeight = 0.3;

    @Column(name = "future_weight")
    private Double futureWeight = 0.2;

    @Column(name = "paradox_boost")
    private Double paradoxBoost = 1.5;

    @Column(name = "nostalgia_factor")
    private Double nostalgiaFactor = 1.2;

    @Column(name = "max_time_jump_days")
    private Integer maxTimeJumpDays = 30;

    @Column(name = "allow_anachronisms")
    private boolean allowAnachronisms = false;

    public ChronoAlgorithmConfig() {
    }

    public ChronoAlgorithmConfig(UUID userId) {
        this.userId = userId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public Double getPastWeight() { return pastWeight; }
    public void setPastWeight(Double pastWeight) { this.pastWeight = pastWeight; }
    public Double getFutureWeight() { return futureWeight; }
    public void setFutureWeight(Double futureWeight) { this.futureWeight = futureWeight; }
    public Double getParadoxBoost() { return paradoxBoost; }
    public void setParadoxBoost(Double paradoxBoost) { this.paradoxBoost = paradoxBoost; }
    public Double getNostalgiaFactor() { return nostalgiaFactor; }
    public void setNostalgiaFactor(Double nostalgiaFactor) { this.nostalgiaFactor = nostalgiaFactor; }
    public Integer getMaxTimeJumpDays() { return maxTimeJumpDays; }
    public void setMaxTimeJumpDays(Integer maxTimeJumpDays) { this.maxTimeJumpDays = maxTimeJumpDays; }
    public boolean isAllowAnachronisms() { return allowAnachronisms; }
    public void setAllowAnachronisms(boolean allowAnachronisms) { this.allowAnachronisms = allowAnachronisms; }
}

