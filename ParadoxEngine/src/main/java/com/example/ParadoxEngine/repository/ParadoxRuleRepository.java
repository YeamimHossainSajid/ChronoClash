package com.example.ParadoxEngine.repository;

import com.example.ParadoxEngine.model.ParadoxRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParadoxRuleRepository extends JpaRepository<ParadoxRule, UUID> {
    List<ParadoxRule> findByIsActiveTrueOrderByPriorityDesc();
}

