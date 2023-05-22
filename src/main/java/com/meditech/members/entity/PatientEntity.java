package com.meditech.members.entity;

import com.meditech.members.dto.MemberDTO;
import com.meditech.members.dto.PatientDTO;
import com.meditech.members.repository.MemberRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "patient_table")
public class PatientEntity {
    @Id //pk지정
    private Long id;

    @Column(name = "patientName")
    private String patientName;

    @Column(name = "patientAge")
    private int patientAge;

    @Column(name = "patientGender")
    private String patientGender;

    @ManyToOne
    @JoinColumn(name="memberId")
    private MemberEntity memberEntity;

    public static PatientEntity toPatientEntity(PatientDTO patientDTO, MemberRepository memberRepository) {//dto객체->entity객체로 변환
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientDTO.getId());
        patientEntity.setPatientName(patientDTO.getPatientName());
        patientEntity.setPatientAge(patientDTO.getPatientAge());
        patientEntity.setPatientGender(patientDTO.getPatientGender());
        Long memberId = patientDTO.getMemberId();
        MemberEntity memberEntity = memberRepository.findById(memberId).orElse(null);
        patientEntity.setMemberEntity(memberEntity);
        return patientEntity;
    }
}
