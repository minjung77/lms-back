package project.lmsback.controller.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.lmsback.domain.Attendance;
import project.lmsback.domain.AttendanceRequestDto;
import project.lmsback.domain.AttendanceStatusDTO;
import project.lmsback.service.AttendanceService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://172.30.1.58:3000"})
@Slf4j
@RestController
@RequestMapping("/api/attendance") // ✅ 첫 번째 "mycourses"
@RequiredArgsConstructor
public class st_AttendanceController {


    private final AttendanceService attendanceService;

    @PostMapping("/video")
    public ResponseEntity<String> markAttendance(@RequestBody AttendanceRequestDto dto, Authentication authentication) {
        Integer stdtId = Integer.parseInt(authentication.getName()); // 🔥 JWT에서 꺼냄
        log.info("📡 출석 요청 도착: lectureId={}, contentId={}, stdtId={}", dto.getLectureId(), dto.getContentId(), stdtId);

        attendanceService.markAttendance(dto.getLectureId(), dto.getContentId(), stdtId);
        return ResponseEntity.ok("출석 처리 완료");
    }

    @GetMapping("/status")
    public ResponseEntity<List<AttendanceStatusDTO>> getAttendanceStatus(Authentication authentication) {
        Integer stdtId = Integer.parseInt(authentication.getName()); // JWT의 sub
        return ResponseEntity.ok(attendanceService.getAttendanceStatusByStudent(stdtId));
    }

}
