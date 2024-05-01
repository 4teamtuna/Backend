package com.example.gsmoa.config;

import com.example.gsmoa.service.social.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// springSecurity Config
@Configuration
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 권한 설정
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("**").permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/chatlogin")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .oauth2Login(oauth2Login -> {
                    oauth2Login
                            .loginPage("/chatlogin")
                            // 여기에서 추가된 OAuth 2.0 사용자 서비스를 등록합니다.
                            .userInfoEndpoint(userInfo -> userInfo
                                    .userService(this.principalOauth2UserService));
                })
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}



