package com.example.TimelineService.repository;

import com.example.TimelineService.model.TimelineBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimelineBranchRepository extends JpaRepository<TimelineBranch, UUID> {
    List<TimelineBranch> findByParentBranchId(UUID parentBranchId);
    List<TimelineBranch> findByIsCanonTrue();
}

