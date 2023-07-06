package com.meditech.members.controller;

import com.meditech.members.dto.PatientDTO;
import com.meditech.members.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;


    @Test
    public void testMainSearch() throws Exception {
        
        //페이징 기능 처리 전 테스트 코드
        // 가짜 데이터 생성
//        List<PatientDTO> fakePatientList = Arrays.asList(
//                new PatientDTO(100L,"최인하",25, "F", 11111L)
//        );
//
//        // memberService의 findSearchAll() 메서드가 호출될 때 가짜 데이터 반환하도록 설정
//        when(memberService.findSearchAll((HttpSession) any(HttpSession.class), anyString())).thenReturn(fakePatientList);
//
//        // POST 요청 시뮬레이션
//        mockMvc.perform(post("/main/search")
//                        .param("patientName", "최인하"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("main2"))
//                .andExpect(model().attributeExists("patientList"))
//                .andExpect(model().attribute("patientList", fakePatientList));
    }

}