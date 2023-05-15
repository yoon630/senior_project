package com.meditech.members.controller;

import com.meditech.members.dto.MemberDTO;
import com.meditech.members.dto.PatientDTO;
import com.meditech.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    //서비스를 위한 생성자 주입
    private final MemberService memberService;

    //기본 페이지(=로그인 페이지) 요청 메소드
    @GetMapping("/")
    public String loginForm(){
        return "login";//=>templates폴더의 login.html을 찾아줌
    }

    @PostMapping("/")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginId", loginResult.getId());//로그인한 id 정보를 세션 저장
            session.setAttribute("loginName", loginResult.getMemberName());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //Long id = (Long)session.getAttribute("id");
        session.invalidate();
        //System.out.println("id: " + id);
        return "login";
    }

    @GetMapping("/select/patient")
    public String findAll(HttpSession session, Model model){//@ModelAttribute 써도되는지 잘 모름
        //System.out.println("id: " + session.getAttribute("id"));
        List<PatientDTO> patientDTOList = memberService.findAll(session);
        model.addAttribute("patientList", patientDTOList);
        return "List";
    }
}
