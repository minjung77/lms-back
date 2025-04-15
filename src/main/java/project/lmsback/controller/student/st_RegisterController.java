package project.lmsback.controller.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.lmsback.domain.*;
import project.lmsback.repository.StudentRepository;
import project.lmsback.service.LectureService;
import project.lmsback.service.RegisterCartService;
import project.lmsback.service.RegisterService;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:3000", "http://172.30.1.58:3000"})
@Slf4j
@RestController
@RequestMapping("/api/mycourses") // ✅ 첫 번째 "mycourses"
@RequiredArgsConstructor

public class st_RegisterController {

    private final LectureService lectureService;
    private final RegisterService registerService;
    private final StudentRepository studentRepository;
    private final RegisterCartService registerCartService;

    // 전체 개설 강좌 목록
    @GetMapping("/enrollment/list")
    public ResponseEntity<?> list() {
        List<LectureDTO> lectureDTO = lectureService.readLecture();

        return new ResponseEntity<>(lectureDTO, HttpStatus.OK);
    }

    // 강의 검색
    @GetMapping({"/find/{type}", "/find/{type}/{keyword}"})
    public List<LectureDTO> findLecture(
            @PathVariable String type,
            @PathVariable(required = false) String keyword
    ) {
        if (keyword == null || keyword.isBlank()) {
            return lectureService.readLecture(); // 키워드 없을 때 전체 목록 반환
        }

        return switch (type) {
            case "subjectName" -> lectureService.findBySubjectName(keyword);
            case "courseType" -> lectureService.findByCourseType(keyword);
            case "department" -> lectureService.findByDepartment(keyword);
            case "subjectCode" -> lectureService.findBySubjectCode(keyword);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    // 수강신청
    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody RegisterClassDTO registerClassDTO) {
        log.info("🟡 프론트에서 받은 학생 ID: {}", registerClassDTO);
        try {
            Integer stdtId = registerClassDTO.getStdtId();
            log.info("🟡 프론트에서 받은 학생 ID: {}", stdtId);

            Optional<StudentInfo> studentOpt = studentRepository.findByStdtId(stdtId);
            if (studentOpt.isEmpty()) {
                log.warn("❌ 해당 ID는 DB에 없음: {}", stdtId);
                throw new IllegalStateException("학생 정보 없음");
            }


            StudentInfo student = studentOpt.get(); // 이제 안전하게 꺼냄
            log.info("🟡 프론트에서 받은 강의 ID: {}", registerClassDTO.getLectureId());

            registerService.register(registerClassDTO);
            return ResponseEntity.ok().build();

        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            log.error("수강신청 중 예외 발생", e);
            return ResponseEntity.internalServerError().body("수강신청 중 오류 발생");
        }
    }

    // 수강신청 내역
    @GetMapping("/enrolledList")
    public ResponseEntity<?> enrolledList(@RequestParam Integer stdtId) {
        List<EnrolledCourseDTO> enrolledList = registerService.getEnrolledCoursesByStdtId(stdtId);
        return new ResponseEntity<>(enrolledList, HttpStatus.OK);
    }

    // 수강취소
    @GetMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam Integer stdtId,
                                    @RequestParam Integer lectureId) {
        try {
            registerService.deleteEnrollment(stdtId, lectureId);
            return ResponseEntity.ok("수강취소 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("수강취소 실패: " + e.getMessage());
        }
    }

    // 장바구니에서 수강신청 페이지에 불러오기

    @GetMapping("/cart/list")
    public ResponseEntity<?> getCartList(@RequestParam Integer stdtId) {
        try {
            List<RegisterCartDTO> cartList = registerCartService.getCartDTOListByStdtId(stdtId);
            return ResponseEntity.ok(cartList);
        } catch (Exception e) {
            log.error("❌ 장바구니 조회 실패", e);
            return ResponseEntity.internalServerError().body("장바구니 조회 중 오류 발생");
        }
    }
    // 우선순위 정하기
    @PostMapping("/cart/priorities")
    public ResponseEntity<?> saveCartPriorities(@RequestBody List<RegisterCartDTO> priorityList) {
        try {
            registerCartService.saveCartPriorities(priorityList);
            return ResponseEntity.ok("🟢 우선순위 저장 성공");
        } catch (Exception e) {
            log.error("❌ 우선순위 저장 실패", e);
            return ResponseEntity.internalServerError().body("우선순위 저장 중 오류 발생");
        }
    }

    // 장바구니용 강의 목록 조회
    @GetMapping("/cart")
    public ResponseEntity<List<LectureDTO>> getCartLectureList() {
        List<LectureDTO> lectures = lectureService.readLecture(); // 현재는 전체 강의 목록 리턴
        return ResponseEntity.ok(lectures);
    }
}

