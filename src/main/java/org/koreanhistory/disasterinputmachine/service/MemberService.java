package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.Member;
import org.koreanhistory.disasterinputmachine.dto.MemberDto;
import org.koreanhistory.disasterinputmachine.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class MemberService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository repository;

    @Transactional
    public List<MemberDto> list() {
        log.info("IN MEMBER SERVICE: list() calling...");
        List<MemberDto> dtoList = new ArrayList<>();
        repository.findAll().forEach(
                member -> dtoList.add(new MemberDto(member))
        );
        return dtoList;
    }

    @Transactional
    public void save(Member member) {
        log.info("IN MEMBER SERVICE: save() calling...");

        String encrptPw = passwordEncoder.encode(member.getUpw());
        member.setUpw(encrptPw);
        repository.save(member);
    }

    public void delete(String uid) {
        repository.deleteById(uid);
    }
}
