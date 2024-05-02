package com.example.gsmoa.config;

import com.example.gsmoa.jwt.JwtAccessDeniedHandler;
import com.example.gsmoa.jwt.JwtAuthenticationEntryPoint;
import com.example.gsmoa.jwt.JwtFilter;
import com.example.gsmoa.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))


                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )

                .apply(new JwtSecurityConfig(tokenProvider));
        return http.build();

    }

}
