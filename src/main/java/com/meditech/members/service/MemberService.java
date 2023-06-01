package com.meditech.members.service;

import com.meditech.members.dto.MemberDTO;
import com.meditech.members.dto.PatientDTO;//나중에 PatientService만들어서 옮겨도 됨
import com.meditech.members.dto.PatientRecordDTO;
import com.meditech.members.entity.MemberEntity;
import com.meditech.members.entity.PatientEntity;
import com.meditech.members.entity.PatientRecordEntity;
import com.meditech.members.repository.MemberRepository;
import com.meditech.members.repository.PatientRecordRepository;
import com.meditech.members.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        Optional<MemberEntity> byId = memberRepository.findById(memberDTO.getId());
        if (byId.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byId.get();//해당 회원의 정보를 엔티티에 저장
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

    private final PatientRepository patientRepository;
    public List<PatientDTO> findAll(HttpSession session){
        Long Id = (Long) session.getAttribute("loginId");
        List<PatientEntity> patientEntityList = patientRepository.findByMemberEntity_Id(Id);
        List<PatientDTO> patientDTOList = new ArrayList<>();
        for(PatientEntity patientEntity: patientEntityList){//여러개의 entity를 여러개의 dto로 하나씩 담기위해
            patientDTOList.add(PatientDTO.toPatientDTO(patientEntity));
        }
        return patientDTOList;
    }

    private final PatientRecordRepository patientRecordRepository;
    public List<PatientRecordDTO> findDetail(Long id) {
        List<PatientRecordEntity> patientRecordEntityList = patientRecordRepository.findByIdPatientEntityId(id);
        List<PatientRecordDTO> patientRecordDTOList = new ArrayList<>();
        for(PatientRecordEntity patientRecordEntity: patientRecordEntityList){
            patientRecordDTOList.add(PatientRecordDTO.toPatientRecordDTO(patientRecordEntity));
        }
        return patientRecordDTOList;
    }

    public void insert(PatientRecordDTO patientRecordDTO, HttpSession session) {
        List<PatientRecordEntity> patientRecordEntityList = patientRecordRepository.findByIdPatientEntityId(patientRecordDTO.getPatientId());
        if(!patientRecordEntityList.isEmpty()){//방문내역이 있으면
            Optional<Integer> latestTurn = patientRecordRepository.findLatestTurnByPatientId(patientRecordDTO.getPatientId());
            patientRecordDTO.setTurn(latestTurn.get() + 1);
        }
        else{
            patientRecordDTO.setTurn(1);
        }

        PatientRecordEntity patientRecordEntity = PatientRecordEntity.toInsertEntity(patientRecordDTO, session, patientRecordRepository);
        patientRecordRepository.save(patientRecordEntity);
    }


}
