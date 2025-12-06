package com.example.ParadoxEngine.controller;

import com.example.ParadoxEngine.model.*;
import com.example.ParadoxEngine.service.ParadoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/paradox")
public class ParadoxController {

    @Autowired
    private ParadoxService paradoxService;

    @PostMapping("/detect")
    public ResponseEntity<ParadoxEvent> detectParadox(@RequestBody DetectParadoxRequest request) {
        ParadoxEvent event = paradoxService.detectParadox(
            request.getType(),
            request.getEntityIds(),
            request.getSeverity(),
            request.getConfidence());
        return ResponseEntity.ok(event);
    }

    @PostMapping("/{id}/resolve")
    public ResponseEntity<ParadoxEvent> resolveParadox(
            @PathVariable UUID id,
            @RequestBody ResolveParadoxRequest request) {
        ParadoxEvent event = paradoxService.resolveParadox(
            id, request.getResolutionMethod(), request.getTimelineBranchId());
        return ResponseEntity.ok(event);
    }

    @GetMapping("/unresolved")
    public ResponseEntity<List<ParadoxEvent>> getUnresolvedParadoxes() {
        return ResponseEntity.ok(paradoxService.getUnresolvedParadoxes());
    }

    @PostMapping("/rules")
    public ResponseEntity<ParadoxRule> createRule(@RequestBody CreateRuleRequest request) {
        ParadoxRule rule = paradoxService.createRule(
            request.getRuleName(),
            request.getCondition(),
            request.getAction(),
            request.getPriority());
        return ResponseEntity.ok(rule);
    }

    @GetMapping("/rules/active")
    public ResponseEntity<List<ParadoxRule>> getActiveRules() {
        return ResponseEntity.ok(paradoxService.getActiveRules());
    }

    @PostMapping("/consistency-check")
    public ResponseEntity<TimelineConsistencyCheck> performConsistencyCheck(
            @RequestParam String checkType) {
        return ResponseEntity.ok(paradoxService.performConsistencyCheck(checkType));
    }

    @PostMapping("/anachronism")
    public ResponseEntity<Anachronism> detectAnachronism(@RequestBody DetectAnachronismRequest request) {
        Anachronism anachronism = paradoxService.detectAnachronism(
            request.getEntityId(),
            request.getEntityType(),
            request.getEntityTimestamp(),
            request.getReferenceTimestamp(),
            request.getDescription());
        return ResponseEntity.ok(anachronism);
    }

    // DTOs
    public static class DetectParadoxRequest {
        private ParadoxType type;
        private List<String> entityIds;
        private Double severity;
        private Double confidence;

        public ParadoxType getType() { return type; }
        public void setType(ParadoxType type) { this.type = type; }
        public List<String> getEntityIds() { return entityIds; }
        public void setEntityIds(List<String> entityIds) { this.entityIds = entityIds; }
        public Double getSeverity() { return severity; }
        public void setSeverity(Double severity) { this.severity = severity; }
        public Double getConfidence() { return confidence; }
        public void setConfidence(Double confidence) { this.confidence = confidence; }
    }

    public static class ResolveParadoxRequest {
        private String resolutionMethod;
        private String timelineBranchId;

        public String getResolutionMethod() { return resolutionMethod; }
        public void setResolutionMethod(String resolutionMethod) { this.resolutionMethod = resolutionMethod; }
        public String getTimelineBranchId() { return timelineBranchId; }
        public void setTimelineBranchId(String timelineBranchId) { this.timelineBranchId = timelineBranchId; }
    }

    public static class CreateRuleRequest {
        private String ruleName;
        private String condition;
        private String action;
        private Integer priority;

        public String getRuleName() { return ruleName; }
        public void setRuleName(String ruleName) { this.ruleName = ruleName; }
        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public Integer getPriority() { return priority; }
        public void setPriority(Integer priority) { this.priority = priority; }
    }

    public static class DetectAnachronismRequest {
        private String entityId;
        private String entityType;
        private LocalDateTime entityTimestamp;
        private LocalDateTime referenceTimestamp;
        private String description;

        public String getEntityId() { return entityId; }
        public void setEntityId(String entityId) { this.entityId = entityId; }
        public String getEntityType() { return entityType; }
        public void setEntityType(String entityType) { this.entityType = entityType; }
        public LocalDateTime getEntityTimestamp() { return entityTimestamp; }
        public void setEntityTimestamp(LocalDateTime entityTimestamp) { this.entityTimestamp = entityTimestamp; }
        public LocalDateTime getReferenceTimestamp() { return referenceTimestamp; }
        public void setReferenceTimestamp(LocalDateTime referenceTimestamp) { this.referenceTimestamp = referenceTimestamp; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}

