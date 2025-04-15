package project.lmsback.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.lmsback.jwt.JwtAuthenticationFilter;
import project.lmsback.security.CustomUserDetailsService;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,JwtAuthenticationFilter   jwtAuthenticationFilter) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/api/auth/**").permitAll() // 로그인, 회원가입은 누구나
                        .antMatchers("/api/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/prof/**").hasRole("PROFESSOR")
                        .antMatchers("/api/student/**").hasRole("STUDENT") // 🔥 student만
                        .antMatchers("/api/mycourses/**").hasRole("STUDENT") // 🔥 여기도 student만
                        .antMatchers("/api/attendance/**").hasRole("STUDENT") // 🔥 여기도 student만
                        .antMatchers("/api/files/**").hasRole("STUDENT") // 파일 다운로드도 제한
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService) // 여기!
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}

