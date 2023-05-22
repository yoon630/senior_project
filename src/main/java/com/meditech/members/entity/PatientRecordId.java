package com.meditech.members.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Primary
@Embeddable
public class PatientRecordId implements Serializable {//복합키를 위한 클래스
    @ManyToOne
    @JoinColumn(name="patientId")
    private PatientEntity patientEntity;
    @Column
    private int turn;
}
