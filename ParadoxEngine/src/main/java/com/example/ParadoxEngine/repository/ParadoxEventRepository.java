package com.example.ParadoxEngine.repository;

import com.example.ParadoxEngine.model.ParadoxEvent;
import com.example.ParadoxEngine.model.ParadoxType;
import com.example.ParadoxEngine.model.ResolutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParadoxEventRepository extends JpaRepository<ParadoxEvent, UUID> {
    List<ParadoxEvent> findByType(ParadoxType type);
    List<ParadoxEvent> findByResolutionStatus(ResolutionStatus status);
    List<ParadoxEvent> findByTimelineBranchId(String timelineBranchId);
}

