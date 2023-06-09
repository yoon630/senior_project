package com.meditech.members.repository;

import com.meditech.members.entity.PatientEntity;
import com.meditech.members.entity.PatientRecordEntity;
import com.meditech.members.entity.PatientRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PatientRecordRepository extends JpaRepository<PatientRecordEntity, PatientRecordId> {
    List<PatientRecordEntity> findByIdPatientEntityId(Long id);//id.patientEntity.id

    @Query(value = "SELECT p.id.turn FROM PatientRecordEntity p WHERE p.id.patientEntity.id = :patientId AND p.visitDate = (SELECT MAX(pr.visitDate) FROM PatientRecordEntity pr WHERE pr.id.patientEntity.id = :patientId)")
    Optional<Integer> findLatestTurnByPatientId(@Param("patientId") Long patientId);
    @Modifying
    @Transactional
    @Query("UPDATE PatientRecordEntity p SET p.reward = :reward, p.action = :action WHERE p.id.patientEntity.id = :patientId AND p.id.turn = :turn")
    void updateRewardAndAction(@Param("patientId") Long patientId, @Param("turn") Integer turn, @Param("reward") double reward, @Param("action") Integer action);
    @Modifying
    @Transactional
    @Query("DELETE FROM PatientRecordEntity p WHERE p.id.patientEntity.id = :patientId AND p.id.turn = (SELECT MAX(pr.id.turn) FROM PatientRecordEntity pr WHERE pr.id.patientEntity.id = :patientId)")
    void deleteLatestRecordByPatientId(@Param("patientId") Long patientId);
}
