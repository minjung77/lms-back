package project.lmsback.controller.student;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.lmsback.domain.AcademicRecordDTO;
import project.lmsback.service.RegisterService;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:3000", "http://172.30.1.58:3000"})
@Slf4j
@RestController
@RequestMapping("/api/mycourses") // ✅ 첫 번째 "mycourses"
@RequiredArgsConstructor
public class st_MypageController {

    private final RegisterService registerService;

    @GetMapping("/score")
    public ResponseEntity<?> getSemesterGrades(
            @RequestParam Integer stdtId,
            @RequestParam String courseYear,
            @RequestParam Integer semesterCd) {

        return ResponseEntity.ok(registerService.getSemesterGrade(stdtId, courseYear, semesterCd));
    }

}
