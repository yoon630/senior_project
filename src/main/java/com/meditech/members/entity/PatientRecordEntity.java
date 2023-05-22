package com.meditech.members.entity;

import com.meditech.members.dto.PatientDTO;
import com.meditech.members.dto.PatientRecordDTO;
import com.meditech.members.repository.MemberRepository;
import com.meditech.members.repository.PatientRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "patient_record_table")
public class PatientRecordEntity {
    @EmbeddedId
    private PatientRecordId id;
    @Column
    private Date visitDate;
    @Column
    private int state;
    @Column
    private int action;
    @Column
    private double reward;
    @Column
    private double blood;//혈액검사 ->정상 or 철분부족 등....
    @Column
    private int ECG;//심전도 검사
    @Column
    private int bloodPressure;//혈압
    @Column
    private Blob xRay;//x_ray 사진
    @Column
    private Blob ultraSound;//초음파 사진

    public static PatientRecordEntity toPatientRecordEntity(PatientRecordDTO patientRecordDTO, PatientRepository patientRepository) {//dto객체->entity객체로 변환
        PatientRecordEntity patientRecordEntity = new PatientRecordEntity();

        Long patientId = patientRecordDTO.getPatientId();
        PatientEntity patientEntity = patientRepository.findById(patientId).orElse(null);
        PatientRecordId recordId = new PatientRecordId();
        recordId.setPatientEntity(patientEntity);
        recordId.setTurn(patientRecordDTO.getTurn());
        patientRecordEntity.setId(recordId);

        LocalDate visitDate = patientRecordDTO.getVisitDate();
        if (visitDate != null) {
            Date convertedVisitDate = new Date(visitDate.toString());
            patientRecordEntity.setVisitDate(convertedVisitDate);
        } else {
            patientRecordEntity.setVisitDate(null);
        }
        patientRecordEntity.setState(patientRecordDTO.getState());
        patientRecordEntity.setAction(patientRecordDTO.getAction());
        patientRecordEntity.setReward(patientRecordDTO.getReward());
        patientRecordEntity.setBlood(patientRecordDTO.getBlood());
        patientRecordEntity.setECG(patientRecordDTO.getECG());
        patientRecordEntity.setBloodPressure(patientRecordDTO.getBloodPressure());
        patientRecordEntity.setXRay(patientRecordDTO.getXRay());
        patientRecordEntity.setUltraSound(patientRecordDTO.getUltraSound());
        return patientRecordEntity;
    }
}
