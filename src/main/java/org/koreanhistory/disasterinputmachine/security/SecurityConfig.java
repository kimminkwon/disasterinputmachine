package org.koreanhistory.disasterinputmachine.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // configure 없이 Annotation으로 Contoller에서 접근 가능
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Remember me 용도
    @Autowired
    DataSource dataSource;

    @Autowired
    HistoryUserService historyUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config.......................");

        String[] staticResources  =  {
                "/css/**",
                "/doc/**",
                "/img/**",
                "/js/**",
        };

        http.authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/**/list").hasAnyRole("BASIC", "MANAGER", "ADMIN")
                .antMatchers("/**/search").hasAnyRole("BASIC", "MANAGER", "ADMIN")
                .antMatchers("/**/view").hasAnyRole("BASIC", "MANAGER", "ADMIN")
                .antMatchers("/**/register").hasAnyRole("BASIC", "MANAGER", "ADMIN")
                .antMatchers("/**/listforonce").hasAnyRole("BASIC", "MANAGER", "ADMIN")
                .antMatchers("/**/modify").hasAnyRole("BASIC", "MANAGER", "ADMIN")
                .antMatchers("/member/**").hasRole("ADMIN")
                .antMatchers("/excel/download").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/excel/detaildownload").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/excel/detailfiledownload").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/excel/filedownload").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/excel/upload").hasAnyRole("ADMIN");

        // 로그인 페이지 & 핸들러 등록
        http.formLogin().loginPage("/login");
        // 접근 거부되었을 때 이동할 페이지
        http.exceptionHandling().accessDeniedPage("/accessdenied");
        // 세선 무효화
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/maintenance/list")
                .invalidateHttpSession(true);
        // user정의 사용자정보 & 권한 묶음을 받음 + rememberMe 기능 추가
        http.rememberMe().key("history")
                .userDetailsService(historyUserService)
                .tokenRepository(getJDBCRepository())
                .tokenValiditySeconds(60 * 60 * 24);
    }

    private PersistentTokenRepository getJDBCRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("build auth global.......................");
        auth.userDetailsService(historyUserService)
                .passwordEncoder(passwordEncoder());
    }
}
