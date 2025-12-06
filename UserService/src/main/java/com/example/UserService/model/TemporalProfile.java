package com.example.UserService.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Embeddable
public class TemporalProfile {

    @ElementCollection
    @CollectionTable(name = "temporal_profile_versions", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "timeline_branch_id")
    @Column(name = "profile_version")
    private Map<String, String> versions = new HashMap<>();

    @Column(name = "current_era")
    private String currentEra;

    @Column(name = "allow_past_edits")
    private boolean allowPastEdits = false;

    @Column(name = "max_time_jump_days")
    private Integer maxTimeJumpDays = 30;

    public TemporalProfile() {
    }

    public Map<String, String> getVersions() {
        return versions;
    }

    public void setVersions(Map<String, String> versions) {
        this.versions = versions;
    }

    public String getCurrentEra() {
        return currentEra;
    }

    public void setCurrentEra(String currentEra) {
        this.currentEra = currentEra;
    }

    public boolean isAllowPastEdits() {
        return allowPastEdits;
    }

    public void setAllowPastEdits(boolean allowPastEdits) {
        this.allowPastEdits = allowPastEdits;
    }

    public Integer getMaxTimeJumpDays() {
        return maxTimeJumpDays;
    }

    public void setMaxTimeJumpDays(Integer maxTimeJumpDays) {
        this.maxTimeJumpDays = maxTimeJumpDays;
    }
}

