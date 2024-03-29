package com.meditech.members.entity;

import com.meditech.members.dto.PatientDTO;
import com.meditech.members.dto.PatientRecordDTO;
import com.meditech.members.repository.PatientRecordRepository;
import com.meditech.members.repository.PatientRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.sql.Blob;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

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
    private String blood;//혈액검사 ->정상/높음/낮음
    @Column
    private int ECG;//심전도 검사
    @Column
    private String bloodPressure;//혈압 최고/최저
    @Column
    private Blob xRay;//x_ray 사진
    @Column
    private Blob ultraSound;//초음파 사진
    @Column(length = 500)
    private String comment;//의사 소견

    public static PatientRecordEntity toInsertEntity(PatientRecordDTO patientRecordDTO, HttpSession session, PatientRecordRepository patientRecordRepository){
        PatientRecordEntity patientRecordEntity1 = new PatientRecordEntity();

        Long patientId = patientRecordDTO.getPatientId();//long
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientId);
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId((long)session.getAttribute("loginId"));
        patientEntity.setMemberEntity(memberEntity);
        PatientRecordId recordId = new PatientRecordId();
        recordId.setPatientEntity(patientEntity);//patient id를 가져오기 위한 patientEntity타입
        recordId.setTurn(patientRecordDTO.getTurn());
//        Optional<Integer> latestTurn = patientRecordRepository.findLatestTurnByPatientId(patientId);
//        if(latestTurn.isPresent()) {//방문 이력이 있으면 그 값을 가져와서 1추가
//            recordId.setTurn(latestTurn.get() + 1);
//        }
//        else{//방문이력없으면 1로 초기화
//            recordId.setTurn(1);
//        }

        patientRecordEntity1.setId(recordId);

        Date currentDate = new Date();
        patientRecordEntity1.setVisitDate(currentDate);

        patientRecordEntity1.setState(patientRecordDTO.getState());
        patientRecordEntity1.setBlood(patientRecordDTO.getBlood());
        patientRecordEntity1.setECG(patientRecordDTO.getECG());
        patientRecordEntity1.setBloodPressure(patientRecordDTO.getBloodPressure());
        patientRecordEntity1.setXRay(patientRecordDTO.getXRay());
        patientRecordEntity1.setUltraSound(patientRecordDTO.getUltraSound());
        patientRecordEntity1.setComment(patientRecordDTO.getComment());
        return patientRecordEntity1;
    }
    public static PatientRecordEntity toPatientRecordEntity(PatientRecordDTO patientRecordDTO, PatientRepository patientRepository) {//dto객체->entity객체로 변환


        PatientRecordEntity patientRecordEntity = new PatientRecordEntity();

        Long patientId = patientRecordDTO.getPatientId();
        PatientEntity patientEntity = patientRepository.findById(patientId).orElse(null);
        PatientRecordId recordId = new PatientRecordId();
        recordId.setPatientEntity(patientEntity);
//        recordId.setTurn(patientRecordDTO.getTurn());
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
        patientRecordEntity.setComment(patientRecordDTO.getComment());
        return patientRecordEntity;
    }
}
