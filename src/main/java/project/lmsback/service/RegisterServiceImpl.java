package project.lmsback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.lmsback.domain.*;
import project.lmsback.repository.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final RegisterClassRepository registerClassRepository;
    private final AcademicRecordRepository academicRecordRepository;

    @Override
    public void register(RegisterClassDTO registerClassDTO) {

        Integer stdtId = registerClassDTO.getStdtId();
        Integer lectureId = registerClassDTO.getLectureId();

        // 학생 조회
        StudentInfo student = studentRepository.findByStdtId(registerClassDTO.getStdtId())
                .orElseThrow(() -> new IllegalStateException("학생 정보 없음"));
        log.info("Student : {}", student.getStdtId());

        // 강의 조회
        LectureInfo lecture = lectureRepository.findByLectureId(registerClassDTO.getLectureId())
                .orElseThrow(() -> new IllegalStateException("강의 정보 없음"));


        // 3. 중복 수강신청 체크
        boolean alreadyExists = registerClassRepository.existsByStdtId_StdtIdAndLectureId_LectureId(stdtId, lectureId);
        if (alreadyExists) {
            throw new IllegalStateException("이미 수강 신청한 과목입니다.");
        }

        // 4. 현재 신청된 총 학점 확인
        List<RegisterClass> enrolledCourses = registerClassRepository.findByStdtId_StdtId(stdtId);

        int totalCredits = enrolledCourses.stream()
                .mapToInt(rc -> Integer.parseInt(rc.getLectureId().getCredit()))
                .sum();

        int newLectureCredit = Integer.parseInt(lecture.getCredit());

        if (totalCredits + newLectureCredit > 18) {
            throw new IllegalStateException("최대 수강 가능 학점(18학점)을 초과할 수 없습니다.");
        }

        // 엔티티 생성 및 세팅
        RegisterClass entity = new RegisterClass();
        entity.setStdtId(student);
        entity.setLectureId(lecture);
        entity.setApplyDate(String.valueOf(registerClassDTO.getApplyDate()));

        // 저장
        registerClassRepository.save(entity);
    }

    // 학생이 수강 신청한 수강목록
    @Override
    public List<EnrolledCourseDTO> getEnrolledCoursesByStdtId(Integer stdtId) {
        List<EnrolledCourseDTO> enrolledList = registerClassRepository.getEnrolledCoursesByStdtId(stdtId);

        return enrolledList;
    }

    // 수강신청삭제
    @Override
    @Transactional
    public void deleteEnrollment(Integer stdtId, Integer lectureId) {
        registerClassRepository.deleteByStdtIdAndLectureId(stdtId,lectureId);
    }

    @Override
    public Map<String, Object> getSemesterGrade(Integer stdtId, String courseYear, Integer semesterCd) {
        Map<String, Object> result = new HashMap<>();

        // 학생 정보 가져오기
        StudentInfo student = studentRepository.findByStdtId(stdtId)
                .orElseThrow(() -> new IllegalArgumentException("학생 정보 없음"));

        // 성적 목록 가져오기
        List<AcademicRecordDTO> grades =
                academicRecordRepository.findGradesByStudentAndSemester(stdtId, courseYear, semesterCd);

        // result에 담기
        result.put("student", Map.of(
                "name", student.getStdtName(),
                "studentId", student.getStdtId(),
                "department", student.getMajor()
        ));
        result.put("grades", grades);

        return result;
    }

}