package com.example.ParadoxEngine.repository;

import com.example.ParadoxEngine.model.TimelineConsistencyCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimelineConsistencyCheckRepository extends JpaRepository<TimelineConsistencyCheck, UUID> {
    List<TimelineConsistencyCheck> findByCheckType(String checkType);
    List<TimelineConsistencyCheck> findByPassedFalse();
    List<TimelineConsistencyCheck> findByCheckTimeAfter(LocalDateTime time);
}

