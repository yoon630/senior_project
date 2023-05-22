package com.meditech.members.repository;

import com.meditech.members.entity.PatientEntity;
import com.meditech.members.entity.PatientRecordEntity;
import com.meditech.members.entity.PatientRecordId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRecordRepository extends JpaRepository<PatientRecordEntity, PatientRecordId> {
    List<PatientRecordEntity> findByIdPatientEntityId(Long id);//id.patientEntity.id
}
