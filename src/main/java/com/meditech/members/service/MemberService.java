package com.meditech.members.service;

import com.meditech.members.dto.MemberDTO;
import com.meditech.members.entity.MemberEntity;
import com.meditech.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    //repository에서 호출하는 메소드가 여기에서 정의됨. repository가 메소드를 호출할 땐 entity객체를 넘겨주어야 함
    //-> dto객체를 entity객체로 변환하는 과정 필요
    //메소드 호출
    //memberRepository.save(memberEntity) 하면 save함수를 통해서 jpa에 의해 insert문이 자동 실행됨
    private final MemberRepository memberRepository;
    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

}
