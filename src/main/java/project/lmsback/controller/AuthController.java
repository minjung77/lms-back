package project.lmsback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import project.lmsback.domain.JwtResponse;
import project.lmsback.domain.LoginRequest;
import project.lmsback.jwt.JwtTokenProvider;

import java.util.Map;

// 교차출처 리소스 공유 CORS
@CrossOrigin(origins = {"http://localhost:3000", "http://172.30.1.58:3000"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("Login request: " + request);
        log.info(">>> login raw password: {}", request.getPassword());
        try {
            // 1️⃣ 사용자 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 2️⃣ 인증 성공 → JWT 토큰 발급
            String token = jwtTokenProvider.generateToken(request.getUsername());
            log.info("Generated token: " + token);

            // 3️⃣ 토큰을 응답으로 반환
            return ResponseEntity.ok(Map.of("accessToken", token));

        } catch (UsernameNotFoundException e) {
            // 4️⃣ 아이디가 존재하지 않는 경우
            log.info(">> UsernameNotFoundException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 존재하지 않습니다!");

        } catch (BadCredentialsException e) {
            // 5️⃣ 비밀번호가 틀린 경우
            log.info(">> BadCredentialsException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디나 비밀번호가 일치하지 않습니다!");

        } catch (Exception e) {
            // 6️⃣ 기타 예외 (예: 이메일 인증 안 됨)
            log.info(">> Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증을 하지 않았습니다!");
        }
    }
}