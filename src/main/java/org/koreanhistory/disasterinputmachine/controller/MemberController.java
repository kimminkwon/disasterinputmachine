package org.koreanhistory.disasterinputmachine.controller;

import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.Member;
import org.koreanhistory.disasterinputmachine.dto.DeleteDataDto;
import org.koreanhistory.disasterinputmachine.dto.MemberDto;
import org.koreanhistory.disasterinputmachine.repository.MemberRepository;
import org.koreanhistory.disasterinputmachine.service.MemberService;
import org.koreanhistory.disasterinputmachine.vo.PageMaker;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log
@Controller
@RequestMapping("/member/")
public class MemberController {
    @Autowired
    MemberService service;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MemberRepository repository;

    @GetMapping("/members")
    public void list(Model model) {
        List<MemberDto> listOfDto = service.list();
        log.info("IN MEMBER CONTROLLER: calling list()...");
        log.info("" + listOfDto);

        model.addAttribute("listOfDto", listOfDto);
    }

    @GetMapping("/join")
    public void join() {
        log.info("IN MEMBER CONTROLLER: join() running...");
    }

    @PostMapping("/join")
    public String joinPost(@ModelAttribute("member") Member member) {
        log.info("IN MEMBER CONTROLLER: joinPost() running...");
        log.info("MEMBER: " + member);

        String encrptPw = passwordEncoder.encode(member.getUpw());
        member.setUpw(encrptPw);
        log.info("AFTER ENCODING MEMBER: " + member);

        repository.save(member);
        return "/member/joinResult";
    }
}
