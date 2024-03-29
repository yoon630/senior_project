package com.meditech.members.controller;

import com.meditech.members.dto.MemberDTO;
import com.meditech.members.dto.PatientDTO;
import com.meditech.members.dto.PatientRecordDTO;
import com.meditech.members.service.MemberService;
import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    //서비스를 위한 생성자 주입
    private final MemberService memberService;
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

    @PostMapping("/insert")
    public String insert(@ModelAttribute PatientRecordDTO patientRecordDTO, HttpSession session){
        memberService.insert(patientRecordDTO, session);
        return "insert";//입력
        //원래 창은 입력 후에도 입력창으로 계속 있어야 함, 결과가 출력되는 팝업창은 별도로 출력되도록 함.
        //잘 입력한 경우에만 /submit 팝업창도 getmapping되도록 if문아래에 getmapping을 해줘야 하나??
        //if else로 해서 잘 입력햇으면 insert창으로, 잘못입력했으면 잘못입력했다는 창?(잘못입력햇다는 창에서 ok버튼 누르면 다시 insert창으로 오도록)
    }

    @GetMapping("/select/data")
    public String dataForm(){
        return "data";
    }

    @GetMapping("/submit")//???
    public String showPopup(){
        return "submit.html";
    }

}
