package com.example.ParadoxEngine.repository;

import com.example.ParadoxEngine.model.Anachronism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnachronismRepository extends JpaRepository<Anachronism, UUID> {
    List<Anachronism> findByEntityId(String entityId);
    List<Anachronism> findByIsIntentionalFalse();
}

