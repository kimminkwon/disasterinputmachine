package org.koreanhistory.disasterinputmachine.security;

import lombok.Getter;
import lombok.Setter;
import org.koreanhistory.disasterinputmachine.domain.Member;
import org.koreanhistory.disasterinputmachine.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HistoryUser extends User {
    private static final String ROLE_PREFIX = "ROLE_";
    private Member member;

    // 생성자 오버라이드
    public HistoryUser(Member member) {
        super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));
        this.member = member;
    }

    private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(
                memberRole -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + memberRole.getRoleName()))
        );
        return list;
    }
}
