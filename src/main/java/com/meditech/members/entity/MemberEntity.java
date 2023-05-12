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
public class MemberEntity {
    @Id //pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
    private Long id;

    @Column(unique = true, name = "memberEmail")//unique 제약 조건 추가
    private String memberEmail;

    @Column(name = "memberPassword")
    private String memberPassword;

    @Column(name = "memberName")
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {//dto객체->entity객체로 변환
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }
}
