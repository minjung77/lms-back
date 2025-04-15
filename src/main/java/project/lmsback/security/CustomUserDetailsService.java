package project.lmsback.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.lmsback.domain.*;
import project.lmsback.repository.*;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
//    private final ProfRepository profInfoRepository;
    private final StudentRepository studentInfoRepository;
    //private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 시도: {}", username);

        int userId;
        try {
            userId = Integer.parseInt(username);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("잘못된 ID 형식입니다.");
        }

        // 1. 관리자 체크
        Optional<Admin> adminOpt = adminRepository.findByAdminId(userId);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            log.info(">>> [관리자 로그인 확인]");
            log.info("입력 ID: {}", userId);
            log.info("DB ID: {}", admin.getAdminId());
            log.info("DB 비밀번호: {}", admin.getPassword());
            log.info("matches 결과: {}", new BCryptPasswordEncoder().matches("1234", admin.getPassword()));
            System.out.println(new BCryptPasswordEncoder().encode("1234"));
            return new CustomUserDetails(admin.getAdminId().toString(), admin.getPassword(), "ADMIN");
        }

//        // 2. 교수 체크
//        Optional<ProfInfo> profOpt = profInfoRepository.findByProfId(userId);
//        if (profOpt.isPresent()) {
//            ProfInfo professor = profOpt.get();
//            log.info(">>> [교수 로그인 확인]");
//            log.info("입력 ID: {}", userId);
//            log.info("DB ID: {}", professor.getProfId());
//            log.info("DB 비밀번호: {}", professor.getPassword());
//            log.info("matches 결과: {}", new BCryptPasswordEncoder().matches("1234", professor.getPassword()));
//            System.out.println(new BCryptPasswordEncoder().encode("1234"));
//            return new CustomUserDetails(professor.getProfId().toString(), professor.getPassword(), "PROFESSOR");
//        }

        // 3. 학생 체크
        Optional<StudentInfo> studentOpt = studentInfoRepository.findByStdtId(userId);
        if (studentOpt.isPresent()) {
            StudentInfo student = studentOpt.get();
            log.info(">>> [학생 로그인 확인]");
            log.info("입력 ID: {}", userId);
            log.info("DB ID: {}", student.getStdtId());
            //log.info("DB 비밀번호: {}", student.getPassword());
            //log.info("matches 결과: {}", new BCryptPasswordEncoder().matches("1234", student.getPassword()));
            //System.out.println(new BCryptPasswordEncoder().encode("1234"));
            return new CustomUserDetails(student.getStdtId().toString(), student.getPassword(), "STUDENT");
        }

        // 모두 실패
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }

}
