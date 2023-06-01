package com.meditech.members.dto;

import com.meditech.members.entity.QtableEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor//기본 생성자 자동 생성
@AllArgsConstructor//필드 전체를 매개변수로 하는 생성자 자동 생성
@ToString
public class QtableDTO {
    private int id; //11, 12, 13 ...
    private double maxQ;

    public static QtableDTO toQtableDTO(QtableEntity qtableEntity) {//entity->dto로 변환
        QtableDTO qtableDTO = new QtableDTO();
        qtableDTO.setId(qtableEntity.getId());
        qtableDTO.setMaxQ(qtableEntity.getMaxQ());
        return qtableDTO;
    }
}
