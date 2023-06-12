package com.meditech.members.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meditech.members.dto.*;
import com.meditech.members.entity.PatientEntity;
import com.meditech.members.entity.PatientRecordEntity;
import com.meditech.members.service.MemberService;
import com.meditech.members.service.QlearningService;
import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    //서비스를 위한 생성자 주입
    private final MemberService memberService;
    private final QlearningService qlearningService;

    //기본 페이지(=로그인 페이지) 요청 메소드
    @GetMapping("/")
    public String loginForm(){
        return "index";//=>templates폴더의 login.html을 찾아줌
    }

    @PostMapping("/")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginId", loginResult.getId());//로그인한 id 정보를 세션 저장
            session.setAttribute("loginName", loginResult.getMemberName());
            List<PatientDTO> patientDTOList = memberService.findAll(session);
            model.addAttribute("patientList", patientDTOList);
            return "main2";
        } else {
            // login 실패
            return "index";
        }
    }
    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        List<PatientDTO> patientDTOList = memberService.findAll(session);
        model.addAttribute("patientList", patientDTOList);
        return "main2";
    }
    @GetMapping("/patient")//새로운 환자 정보 입력 페이지로
    public String patientForm(){
        return "patient";//새로운 환자 정보 입력 페이지 폼 뜨게
    }

    @PostMapping("/patient")
    public String register(@ModelAttribute PatientDTO patientDTO, HttpSession session){//환자 상태 입력 페이지에서 동작
        try {
            // 예외가 발생할 수 있는 코드
            System.out.println("new Patient ID: " + patientDTO.getId()); // 콘솔 출력
            memberService.register(patientDTO, session);
        } catch (Exception e) {
            e.printStackTrace(); // 스택 트레이스 출력
        }

        return "patient";//입력
        //원래 창은 입력 후에도 입력창으로 계속 있어야 함, 결과가 출력되는 팝업창은 별도로 출력되도록 함.
        //잘 입력한 경우에만 /submit 팝업창도 getmapping되도록 if문아래에 getmapping을 해줘야 하나??
        //if else로 해서 잘 입력햇으면 insert창으로, 잘못입력했으면 잘못입력했다는 창?(잘못입력햇다는 창에서 ok버튼 누르면 다시 insert창으로 오도록)
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //Long id = (Long)session.getAttribute("id");
        session.invalidate();
        //System.out.println("id: " + id);
        return "index";
    }

    @GetMapping("/select/patient/{id}")//해당환자의 진료내역조회
    public String findDetail(@PathVariable Long id, Model model){
        List<PatientRecordDTO> patientRecordDTOList = memberService.findDetail(id);
        model.addAttribute("patientRecordList", patientRecordDTOList);
        return "detail";
    }

    @GetMapping("/insert")
    public String insertForm(){
        return "insert";
    }


    long patientId = 0;
    List<QtableDTO> qtableDTOList;
    String patientName;
    @PostMapping("/insert")
    public String insert(@ModelAttribute PatientRecordDTO patientRecordDTO, HttpSession session){//환자 상태 입력 페이지에서 동작
        patientName = memberService.findPatientName(patientRecordDTO.getPatientId());
//        session.setAttribute("patientName",patientName);
        patientId = patientRecordDTO.getPatientId();
        System.out.println("전역으로 설정한 patientID를 insert함수에서: " + patientId);
        //session.setAttribute("patientId",patientRecordDTO.getPatientId());
        try {
            // 예외가 발생할 수 있는 코드
            System.out.println("Patient ID: " + patientRecordDTO.getPatientId()); // 콘솔 출력
            patientRecordDTO = memberService.insert(patientRecordDTO, session);
        } catch (Exception e) {
            e.printStackTrace(); // 스택 트레이스 출력
        }
        qtableDTOList =memberService.allMaxQ();
        qlearningService.qlearning(patientRecordDTO, session);
        return "insert";//입력
        //원래 창은 입력 후에도 입력창으로 계속 있어야 함, 결과가 출력되는 팝업창은 별도로 출력되도록 함.
        //잘 입력한 경우에만 /submit 팝업창도 getmapping되도록 if문아래에 getmapping을 해줘야 하나??
        //if else로 해서 잘 입력햇으면 insert창으로, 잘못입력했으면 잘못입력했다는 창?(잘못입력햇다는 창에서 ok버튼 누르면 다시 insert창으로 오도록)
    }
    @GetMapping("/submit")//???
    public String showPopup(HttpSession session, Model model) throws InterruptedException {
        Thread.sleep(1000);//getmapping지연시키기

        //큐러닝 결과 선택된 action 가져오기
        System.out.println("컨트롤러에서 patientId = "+patientId);
        PatientRecordDTO patientRecordDTO = memberService.findResult(patientId);
        model.addAttribute("patientName", patientName);
        model.addAttribute("patientRecordDTO", patientRecordDTO);
        for (int i = 0; i < qtableDTOList.size(); i++) {
            QtableDTO qtableDTO = qtableDTOList.get(i);
            model.addAttribute("qtableDTO" + i, qtableDTO);
        }
        return "submit.html";
    }
    @GetMapping("/select/data")
    public String dataForm(Model model) throws JsonProcessingException {
        //model에 그래프로 넘길 값 (episode테이블 값들) 넣어주면댐
        List<QtableDTO> qtableDTOList = memberService.allMaxQ();
        //List<EpisodeDTO> episodeDTOList = memberService.allEpsiode();
        List<Double> epsilonList = memberService.allEpsilon();
        List<Integer> idList = memberService.allId();
        List<Double> s1List = memberService.allS1();
        List<Double> s2List = memberService.allS2();
        List<Double> s3List = memberService.allS3();
        List<Double> s4List = memberService.allS4();
        List<Double> s5List = memberService.allS5();
        List<Double> s6List = memberService.allS6();

        ObjectMapper objectMapper = new ObjectMapper();//json형태로 변경
        String jsonEpsilonList = objectMapper.writeValueAsString(epsilonList);
        String jsonIdList = objectMapper.writeValueAsString(idList);
        String jsonS1List = objectMapper.writeValueAsString(s1List);
        String jsonS2List = objectMapper.writeValueAsString(s2List);
        String jsonS3List = objectMapper.writeValueAsString(s3List);
        String jsonS4List = objectMapper.writeValueAsString(s4List);
        String jsonS5List = objectMapper.writeValueAsString(s5List);
        String jsonS6List = objectMapper.writeValueAsString(s6List);
        for (int i = 0; i < qtableDTOList.size(); i++) {
            QtableDTO qtableDTO = qtableDTOList.get(i);
            model.addAttribute("qtableDTO" + i, qtableDTO);
        }

        model.addAttribute("jsonEpsilonList", jsonEpsilonList);
        model.addAttribute("jsonIdList", jsonIdList);
        model.addAttribute("jsonS1List", jsonS1List);
        model.addAttribute("jsonS2List", jsonS2List);
        model.addAttribute("jsonS3List", jsonS3List);
        model.addAttribute("jsonS4List", jsonS4List);
        model.addAttribute("jsonS5List", jsonS5List);
        model.addAttribute("jsonS6List", jsonS6List);

        //model.addAttribute("episodeDTOList", episodeDTOList);//episode에 따른 Qmax 그래프를 위한 데이터

        return "data";
    }

}
