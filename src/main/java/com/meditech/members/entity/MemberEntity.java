package com.meditech.members.entity;

import com.meditech.members.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//entity클래스를 통해 db의 table을 생성함 = 자바언어를 통해서 db를 생성하도록 하는 파일임

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {//의사 엔티티
    @Id //pk지정
    private Long id;

    @Column(name = "memberPassword")
    private String memberPassword;

    @Column(name = "memberName")
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {//dto객체->entity객체로 변환
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }
}
