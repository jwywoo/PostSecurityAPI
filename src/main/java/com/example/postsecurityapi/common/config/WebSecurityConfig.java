package com.example.postsecurityapi.common.config;

import com.example.postsecurityapi.common.filter.JwtAuthenticationFilter;
import com.example.postsecurityapi.common.filter.JwtAuthorizationFilter;
import com.example.postsecurityapi.common.jwt.JwtUtil;
import com.example.postsecurityapi.common.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter()throws Exception{
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter(jwtUtil,userDetailsService);
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf.disable());

        http.sessionManagement((sessionManagement)->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests)->
                        authorizeHttpRequests
                                .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
                                .requestMatchers("/api/user/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtAuthorizationFilter(),JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
