package org.koreanhistory.disasterinputmachine.repository;

import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.Member;
import org.koreanhistory.disasterinputmachine.domain.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 데이터비우기() {
        repository.deleteAll();
    }

    @Test
    public void 예제데이터삽입() {
        Member admin = new Member();
        admin.setUid("adminId");
        admin.setUpw("admin");
        admin.setUname("관리자");
        MemberRole roleAdmin = new MemberRole();
        roleAdmin.setRoleName("ADMIN");
        admin.setRoles(Arrays.asList(roleAdmin));
        repository.save(admin);

        Member manager = new Member();
        manager.setUid("managerId");
        manager.setUpw("manager");
        manager.setUname("매니저");
        MemberRole roleManager = new MemberRole();
        roleManager.setRoleName("MANAGER");
        manager.setRoles(Arrays.asList(roleManager));
        repository.save(manager);

        Member basic = new Member();
        basic.setUid("basicId");
        basic.setUpw("basic");
        basic.setUname("일반회원");
        MemberRole roleBasic = new MemberRole();
        roleBasic.setRoleName("BASIC");
        basic.setRoles(Arrays.asList(roleBasic));
        repository.save(basic);
    }

    @Test
    public void 데이터암호화() {
        Iterable<Member> all = repository.findAll();
        all.forEach(
                member ->  {
                    member.setUpw(passwordEncoder.encode(member.getUpw()));
                    repository.save(member);
                }
        );
    }

}
