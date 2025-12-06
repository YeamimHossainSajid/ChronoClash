package com.example.ParadoxEngine.service;

import com.example.ParadoxEngine.model.*;
import com.example.ParadoxEngine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ParadoxService {

    @Autowired
    private ParadoxEventRepository paradoxEventRepository;

    @Autowired
    private ParadoxRuleRepository paradoxRuleRepository;

    @Autowired
    private TimelineConsistencyCheckRepository consistencyCheckRepository;

    @Autowired
    private AnachronismRepository anachronismRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public ParadoxEvent detectParadox(ParadoxType type, List<String> entityIds, Double severity, Double confidence) {
        ParadoxEvent event = new ParadoxEvent();
        event.setType(type);
        event.setInvolvedEntityIds(entityIds);
        event.setSeverity(severity);
        event.setConfidence(confidence);
        event = paradoxEventRepository.save(event);
        kafkaTemplate.send("paradox-events", event.getId().toString(), event);
        return event;
    }

    public ParadoxEvent resolveParadox(UUID id, String resolutionMethod, String timelineBranchId) {
        ParadoxEvent event = paradoxEventRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Paradox event not found"));
        event.setResolutionStatus(ResolutionStatus.RESOLVED);
        event.setResolutionMethod(resolutionMethod);
        event.setTimelineBranchId(timelineBranchId);
        event = paradoxEventRepository.save(event);
        kafkaTemplate.send("resolution-events", event.getId().toString(), event);
        return event;
    }

    public List<ParadoxEvent> getUnresolvedParadoxes() {
        return paradoxEventRepository.findByResolutionStatus(ResolutionStatus.DETECTED);
    }

    public ParadoxRule createRule(String ruleName, String condition, String action, Integer priority) {
        ParadoxRule rule = new ParadoxRule();
        rule.setRuleName(ruleName);
        rule.setCondition(condition);
        rule.setAction(action);
        rule.setPriority(priority);
        return paradoxRuleRepository.save(rule);
    }

    public List<ParadoxRule> getActiveRules() {
        return paradoxRuleRepository.findByIsActiveTrueOrderByPriorityDesc();
    }

    public TimelineConsistencyCheck performConsistencyCheck(String checkType) {
        TimelineConsistencyCheck check = new TimelineConsistencyCheck();
        check.setCheckType(checkType);
        check.setPassed(true); // Simplified - would contain actual logic
        check = consistencyCheckRepository.save(check);
        return check;
    }

    public Anachronism detectAnachronism(String entityId, String entityType, LocalDateTime entityTimestamp, 
                                         LocalDateTime referenceTimestamp, String description) {
        Anachronism anachronism = new Anachronism();
        anachronism.setEntityId(entityId);
        anachronism.setEntityType(entityType);
        anachronism.setEntityTimestamp(entityTimestamp);
        anachronism.setReferenceTimestamp(referenceTimestamp);
        anachronism.setDetectedAnachronism(description);
        anachronism.setAnachronismScore(calculateAnachronismScore(entityTimestamp, referenceTimestamp));
        return anachronismRepository.save(anachronism);
    }

    private Double calculateAnachronismScore(LocalDateTime entityTime, LocalDateTime referenceTime) {
        long hoursDiff = java.time.Duration.between(entityTime, referenceTime).toHours();
        return Math.abs(hoursDiff) / 24.0; // Score based on days difference
    }
}

