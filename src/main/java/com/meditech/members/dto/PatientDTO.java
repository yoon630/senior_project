package com.meditech.members.dto;

import com.meditech.members.entity.MemberEntity;
import com.meditech.members.entity.PatientEntity;
import lombok.*;

@Getter//private필드의 getter 자동 생성
@Setter//setter 자동 생성
@NoArgsConstructor//기본 생성자 자동 생성
@AllArgsConstructor//필드 전체를 매개변수로 하는 생성자 자동 생성
@ToString
public class PatientDTO {
    private Long id;
    private String patientName;
    private int patientAge;
    private String patientGender;
    private Long memberId;//참조키

    public static PatientDTO toPatientDTO(PatientEntity patientEntity) {//entity->dto로 변환
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patientEntity.getId());
        patientDTO.setPatientName(patientEntity.getPatientName());
        patientDTO.setPatientAge(patientEntity.getPatientAge());
        patientDTO.setPatientGender(patientEntity.getPatientGender());
        patientDTO.setMemberId(patientEntity.getMemberEntity().getId());
        return patientDTO;
    }
}
