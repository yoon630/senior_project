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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
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
    private final PatientRecordRepository patientRecordRepository1;
    public PatientRecordDTO insert(PatientRecordDTO patientRecordDTO, HttpSession session) throws IOException {
        List<PatientRecordEntity> patientRecordEntityList = patientRecordRepository1.findByIdPatientEntityId(patientRecordDTO.getPatientId());
        if(!patientRecordEntityList.isEmpty()){//방문내역이 있으면
            //마지막 레코드 삭제 하는 과정 (어차피 2차 방문부터는 그에 대한 레코드가 이미 생성되어 있으며, 현재가 되는 state값만 확인하면 되므로)
            int latestTurn = patientRecordRepository1.findMaxTurn(patientRecordDTO.getPatientId());
            System.out.println("latestTurn: " + latestTurn); // 콘솔 출력
            patientRecordRepository1.deleteLatestRecordByPatientId(patientRecordDTO.getPatientId(), latestTurn);
            //그후
            latestTurn = patientRecordRepository1.findMaxTurn(patientRecordDTO.getPatientId());
            patientRecordDTO.setTurn(latestTurn + 1);
        }
        else{
            patientRecordDTO.setTurn(1);
        }

        //파일 첨부 여부에 따라 로직 분리
        if(patientRecordDTO.getXRay().isEmpty()&&patientRecordDTO.getUltraSound().isEmpty()){
            //첨부 파일 없음.
            PatientRecordEntity patientRecordEntity = PatientRecordEntity.toInsertEntity(patientRecordDTO, session);
            patientRecordRepository1.save(patientRecordEntity);
        }
        else{
            //첨부파일 있음.
            String originalxRayFileName = null;
            String storedxRayFileName = null;
            String originalultraSoundFileName = null;
            String storedultraSoundFileName = null;
            if(!patientRecordDTO.getXRay().isEmpty()){//xray파일 있으면
                MultipartFile xRay = patientRecordDTO.getXRay();
                originalxRayFileName = patientRecordDTO.getOriginalxRayFileName();
                storedxRayFileName = System.currentTimeMillis()+"_"+originalxRayFileName;
                String savePath  = "C:/Users/kms47/Downloads/종설/imgs/" + storedxRayFileName;
                xRay.transferTo(new File(savePath));
            }
            if(!patientRecordDTO.getUltraSound().isEmpty()){//ultrasound파일 있으면
                MultipartFile ultraSound = patientRecordDTO.getUltraSound();
                originalultraSoundFileName = patientRecordDTO.getOriginalultraSoundFileName();
                storedultraSoundFileName = System.currentTimeMillis()+"_"+originalultraSoundFileName;
                String savePath  = "C:/Users/kms47/Downloads/종설/imgs/" + storedultraSoundFileName;
                ultraSound.transferTo(new File(savePath));
            }
            PatientRecordEntity patientRecordEntity = PatientRecordEntity.toInsertFileEntity(patientRecordDTO, session, originalxRayFileName, originalultraSoundFileName, storedxRayFileName, storedultraSoundFileName);
            patientRecordRepository1.save(patientRecordEntity);
        }
        return patientRecordDTO;
    }

    public void register(PatientDTO patientDTO, HttpSession session) throws IOException{
        Optional<PatientEntity> patientEntity = patientRepository.findById(patientDTO.getId());
        if(!patientEntity.isPresent()){//해당 아이디 존재하지 않으면
            patientDTO.setMemberId((long)session.getAttribute("loginId"));
            PatientEntity patientEntity1 = PatientEntity.toPatientEntity(patientDTO,memberRepository);
            patientRepository.save(patientEntity1);
        }
//        else{
//
//        }
    }

    public String findPatientName(Long patientId) {
        String patientName = patientRepository.findNameById(patientId);
        return patientName;
    }
}
