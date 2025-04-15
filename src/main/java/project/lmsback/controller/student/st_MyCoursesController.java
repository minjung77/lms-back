package project.lmsback.controller.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.lmsback.domain.*;
import project.lmsback.service.MyCourserService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://172.30.1.58:3000"})
@Slf4j
@RestController
@RequestMapping("/api/mycourses") // ✅ 첫 번째 "mycourses"
@RequiredArgsConstructor
public class st_MyCoursesController {

    private final MyCourserService myCourserService;

    // 학생이 수강 중인 강의 목록 조회
    @GetMapping("/mycourses")
    public List<MycourseDTO> getMyCourses(@AuthenticationPrincipal UserDetails userDetails) {
        // CustomUserDetailsService에서 설정한 username = stdtId
        Integer stdtId = Integer.parseInt(userDetails.getUsername());

        List<MycourseDTO> courses = myCourserService.getCoursesByStudentId(stdtId);
        log.info("🎯 로그인한 학생 ID: {}, 강의 수: {}", stdtId, courses.size());

        return courses;
    }

    // 특정 강의의 주차 목록 조회
    @GetMapping("/{lectureId}/weeks")
    public List<LectureWeekDTO> getWeeks(@PathVariable Integer lectureId) {
        return myCourserService.getWeeksByLectureId(lectureId);
    }

//    // 특정 주차의 콘텐츠 목록 조회
//    @GetMapping("/weeks/{weekId}/contents")
//    public List<LectureContentDTO> getContents(@PathVariable Integer weekId) {
//        return myCourserService.getContentsByWeekId(weekId);
//    }

    // 특정 강의와 주차의 콘텐츠 목록 조회
    @GetMapping("/{lectureId}/week/{weekNumber}/contents")
    public List<LectureContentDTO> getContentsByLectureAndWeek(
            @PathVariable Integer lectureId,
            @PathVariable Integer weekNumber) {
        return myCourserService.getContentsByLectureAndWeek(lectureId, weekNumber);
    }
    @GetMapping("/{lectureId}/assignments")
    public List<AssignmentDTO> getAssignments(@PathVariable Integer lectureId) {
        return myCourserService.getAssignmentsByLectureId(lectureId);
    }


    
    @GetMapping("/assignments/{assignmentId}/submit")
    public AssignmentSubmitDTO getSubmitStatus(@PathVariable Integer assignmentId, Authentication authentication) {
        // ✅ JWT에서 추출된 사용자 ID 가져오기
        String username = authentication.getName(); // sub에서 온 값 (ex. "20250001")
        Integer stdtId = Integer.parseInt(username);

        return myCourserService.getSubmitStatus(assignmentId, stdtId);
    }



    @GetMapping("/{lectureId}/week/{weekNumber}/assignment")
    public ResponseEntity<AssignmentDTO> getAssignmentByLectureAndWeek(
            @PathVariable Integer lectureId,
            @PathVariable Integer weekNumber
    ) {
        AssignmentDTO assignment = myCourserService.getAssignmentByLectureAndWeek(lectureId, weekNumber);
        return ResponseEntity.ok(assignment);
    }




    //
    @PostMapping("/submit")
    public ResponseEntity<String> submitAssignment(
            @RequestParam("file") MultipartFile file,
            @RequestParam("lectureId") Integer lectureId,
            @RequestParam("weekNumber") Integer weekNumber,
            Authentication authentication  // ✅ 추가
    ) {
        // ✅ JWT 토큰의 sub에서 stdtId 추출
        Integer stdtId = Integer.parseInt(authentication.getName());

        myCourserService.saveAssignmentSubmit(file, lectureId, weekNumber, stdtId);
        return ResponseEntity.ok("제출 완료");
    }




}
