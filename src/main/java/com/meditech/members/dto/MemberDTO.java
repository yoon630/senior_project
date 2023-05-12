package com.meditech.members.dto;

import com.meditech.members.entity.MemberEntity;
import lombok.*;

@Getter//private필드의 getter 자동 생성
@Setter//setter 자동 생성
@NoArgsConstructor//기본 생성자 자동 생성
@AllArgsConstructor//필드 전체를 매개변수로 하는 생성자 자동 생성
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {//entity->dto로 변환
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}
