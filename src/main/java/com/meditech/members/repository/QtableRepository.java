package com.meditech.members.repository;

import com.meditech.members.entity.QtableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface QtableRepository extends JpaRepository<QtableEntity, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE QtableEntity q SET q.maxQ = :maxQ WHERE q.id = :id")
    void updateMaxQ(@Param("id") Integer id, @Param("maxQ") double maxQ);

    @Query(value = "SELECT q.maxQ FROM QtableEntity q WHERE q.id = :id")
    Optional<Double> findMaxQ(@Param("id") int id);
}
