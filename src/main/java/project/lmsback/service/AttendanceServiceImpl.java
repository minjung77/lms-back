package project.lmsback.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.lmsback.domain.*;
import project.lmsback.repository.AttendanceRepository;
import project.lmsback.repository.FileRepository;
import project.lmsback.repository.LectureContentRepository;
import project.lmsback.repository.RegisterClassRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final RegisterClassRepository registerClassRepository;
    private final LectureContentRepository lectureContentRepository;

    @Override
    public void markAttendance(Integer lectureId, Integer contentId, Integer stdtId) {
        log.info("🎯 출석 처리 로직 진입 - lectureId={}, contentId={}, stdtId={}", lectureId, contentId, stdtId);

        RegisterClass registerClass = registerClassRepository
                .findByLectureId_LectureIdAndStdtId_StdtId(lectureId, stdtId)
                .orElseThrow(() -> new RuntimeException("❌ 수강 정보를 찾을 수 없습니다."));

        LectureContent content = lectureContentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("❌ 콘텐츠가 존재하지 않습니다."));

        boolean alreadyMarked = attendanceRepository
                .findByRegisterClassAndContent(registerClass, content)
                .isPresent();

        if (!alreadyMarked) {
            Attendance attendance = new Attendance();
            attendance.setRegisterClass(registerClass);
            attendance.setContent(content);
            attendance.setIsAttended(true);
            attendance.setAttendTime(LocalDateTime.now());
            attendanceRepository.save(attendance);

            log.info("✅ 출석 저장 완료!");
        } else {
            log.info("⛔ 이미 출석한 콘텐츠");
        }
    }

    @Override
    public List<AttendanceStatusDTO> getAttendanceStatusByStudent(Integer stdtId) {
        List<RegisterClass> registerClasses = registerClassRepository.findByStdtId_StdtId(stdtId);
        List<AttendanceStatusDTO> result = new ArrayList<>();

        for (RegisterClass reg : registerClasses) {
            LectureInfo lecture = reg.getLectureId();
            List<LectureContent> contents = lectureContentRepository.findByLecture_LectureId(lecture.getLectureId());

            int total = contents.size();
            int attended = 0;
            List<WeekAttendanceDTO> weeklyStatus = new ArrayList<>();

            for (LectureContent content : contents) {
                Optional<Attendance> record = attendanceRepository.findByRegisterClassAndContent(reg, content);
                String status;

                if (record.isPresent() && Boolean.TRUE.equals(record.get().getIsAttended())) {
                    attended++;
                    status = "출석";
                } else {
                    status = "결석";
                }

                // 여기서 주차 번호와 제목 가져오기
                int weekNumber = content.getWeek().getWeekNumber();
                String weekTitle = content.getChapterName(); // 또는 content.getWeek().getTitle() 등 원하는 값

                weeklyStatus.add(new WeekAttendanceDTO(weekNumber, weekTitle, status));
            }

            int rate = total == 0 ? 0 : (int) ((attended / (double) total) * 100);

            AttendanceStatusDTO dto = new AttendanceStatusDTO(
                    lecture.getLectureId(),
                    lecture.getSubjectName(),
                    lecture.getProfessor().getProfName(),
                    rate,
                    weeklyStatus
            );

            result.add(dto);
        }

        return result;
    }
}