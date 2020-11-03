package org.koreanhistory.disasterinputmachine.security;

import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log
public class HistoryUserService implements UserDetailsService {

    @Autowired
    MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Member Type의 조회를 UserDetails Type으로 변환 필요
        UserDetails userDetails = repository.findById(username)
                .filter(member -> member != null)
                .map(member -> new HistoryUser(member)).get();

        return userDetails;
    }
}
