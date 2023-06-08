package com.meditech.members.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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
    @Column(nullable = false) //자동 증가 못쓴대
    //@GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    private int turn = 1;

    // equals() 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientRecordId that = (PatientRecordId) o;

        if (turn != that.turn) return false;
        return Objects.equals(patientEntity, that.patientEntity);
    }

    // hashCode() 메서드 구현
    @Override
    public int hashCode() {
        int result = patientEntity != null ? patientEntity.hashCode() : 0;
        result = 31 * result + turn;
        return result;
    }

}
