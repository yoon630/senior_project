package com.meditech.members.repository;

import com.meditech.members.entity.PatientEntity;
import com.meditech.members.entity.PatientRecordEntity;
import com.meditech.members.entity.PatientRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRecordRepository extends JpaRepository<PatientRecordEntity, PatientRecordId> {
    List<PatientRecordEntity> findByIdPatientEntityId(Long id);//id.patientEntity.id

    @Query(value = "SELECT p.id.turn FROM PatientRecordEntity p WHERE p.id.patientEntity.id = :patientId AND p.visitDate = (SELECT MAX(pr.visitDate) FROM PatientRecordEntity pr WHERE pr.id.patientEntity.id = :patientId)")
    Optional<Integer> findLatestTurnByPatientId(@Param("patientId") Long patientId);
}
