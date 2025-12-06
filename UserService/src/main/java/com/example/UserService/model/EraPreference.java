package com.example.UserService.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "era_preferences")
public class EraPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "era_name", nullable = false)
    private String eraName;

    @Column(name = "aesthetic_style", columnDefinition = "TEXT")
    private String aestheticStyle;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public EraPreference() {
    }

    public EraPreference(User user, String eraName, String aestheticStyle) {
        this.user = user;
        this.eraName = eraName;
        this.aestheticStyle = aestheticStyle;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEraName() {
        return eraName;
    }

    public void setEraName(String eraName) {
        this.eraName = eraName;
    }

    public String getAestheticStyle() {
        return aestheticStyle;
    }

    public void setAestheticStyle(String aestheticStyle) {
        this.aestheticStyle = aestheticStyle;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

