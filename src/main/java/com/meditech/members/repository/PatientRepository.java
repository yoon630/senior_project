package com.meditech.members.repository;

import com.meditech.members.entity.MemberEntity;
import com.meditech.members.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
//로그인한 세션의 값과 동일한 의사 id를 참조하는 환자만 출력해야함
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    //Optional<PatientEntity> findByMemberId(Long memberId);
    //Optional<PatientEntity> findByMemberIdAndMember_Id(MemberEntity memberEntity, Long id);
    //이거 memberId랑 세션에 저장된 id을 비교하게 되어있는데, 나중에 바꾸기
    List<PatientEntity> findByMemberEntity_Id(Long id);
    Optional<PatientEntity> findById(Long id);
    @Query("SELECT p.patientName from PatientEntity p WHERE p.id = :id")
    String findNameById(@Param("id") Long id);
}
