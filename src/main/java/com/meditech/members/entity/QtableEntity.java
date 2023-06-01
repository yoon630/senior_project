package com.meditech.members.entity;

import com.meditech.members.dto.MemberDTO;
import com.meditech.members.dto.QtableDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "q_table")
public class QtableEntity {
    @Id
    private int id;

    @Column
    private double maxQ;

    public static QtableEntity toQtableEntity(QtableDTO qtableDTO) {//dto객체->entity객체로 변환
        QtableEntity qtableEntity = new QtableEntity();
        qtableEntity.setId(qtableDTO.getId());
        qtableEntity.setMaxQ(qtableDTO.getMaxQ());
        return qtableEntity;
    }
}
