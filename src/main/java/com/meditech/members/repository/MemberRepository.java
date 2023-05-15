package com.meditech.members.repository;

import com.meditech.members.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//데이터베이스와 연동되는 파일, repository는 entity를 받아와서 사용
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {//long=MemberEntity의 pk가 어떤 타입인지를 써줌
    // 이메일로 회원 정보 조회 (select * from member_table where id=?)
    Optional<MemberEntity> findById(Long id);
}
