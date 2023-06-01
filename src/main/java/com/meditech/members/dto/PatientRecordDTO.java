package com.meditech.members.dto;

import com.meditech.members.entity.PatientEntity;
import com.meditech.members.entity.PatientRecordEntity;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;

@Getter//private필드의 getter 자동 생성
@Setter//setter 자동 생성
@NoArgsConstructor//기본 생성자 자동 생성
@AllArgsConstructor//필드 전체를 매개변수로 하는 생성자 자동 생성
@ToString
public class PatientRecordDTO {
    private Long patientId;
    private int turn;//방문회차
    private LocalDate visitDate;
    private int state;
    private int action;
    private double reward;
    private String blood;//혈액검사
    private int ECG;//심전도 검사
    private String bloodPressure;//혈압
    private Blob xRay;//x_ray 사진
    private Blob ultraSound;//초음파 사진
    private String comment;//의사 소견

    public static PatientRecordDTO toPatientRecordDTO(PatientRecordEntity patientRecordEntity) {//entity->dto로 변환
        PatientRecordDTO patientRecordDTO = new PatientRecordDTO();
        patientRecordDTO.setPatientId(patientRecordEntity.getId().getPatientEntity().getId());
        patientRecordDTO.setTurn(patientRecordEntity.getId().getTurn());

        Date visitDate = patientRecordEntity.getVisitDate();
        if (visitDate != null) {
            LocalDate convertedVisitDate = new java.sql.Date(visitDate.getTime()).toLocalDate();
            patientRecordDTO.setVisitDate(convertedVisitDate);
        } else {
            patientRecordDTO.setVisitDate(null);
        }
        patientRecordDTO.setState(patientRecordEntity.getState());
        patientRecordDTO.setAction(patientRecordEntity.getState());
        patientRecordDTO.setReward(patientRecordEntity.getReward());
        patientRecordDTO.setBlood(patientRecordEntity.getBlood());
        patientRecordDTO.setECG(patientRecordEntity.getECG());
        patientRecordDTO.setBloodPressure(patientRecordEntity.getBloodPressure());
        patientRecordDTO.setXRay(patientRecordEntity.getXRay());
        patientRecordDTO.setUltraSound(patientRecordEntity.getUltraSound());
        patientRecordDTO.setComment(patientRecordEntity.getComment());
        return patientRecordDTO;
    }
}
