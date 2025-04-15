package project.lmsback.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.lmsback.domain.*;
import project.lmsback.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyCourserServiceImpl implements MyCourserService {
    private final RegisterClassRepository registerClassRepository;
    private final LectureWeekRepository lectureWeekRepository;
    private final LectureContentRepository lectureContentRepository;
    private final LectureAssignmentRepository lectureAssignmentRepository;
    private final AssignmentSubmitRepository assignmentSubmitRepository;

    @Override
    public List<MycourseDTO> getCoursesByStudentId(Integer stdtId) {
        log.info("getCoursesByStudentId: {}", stdtId);

        List<RegisterClass> registered = registerClassRepository.findByStdtId_StdtId(stdtId);

        log.info("등록된 수강 강의 개수: {}", registered.size());

        return registered.stream().map(reg -> {
            LectureInfo lecture = reg.getLectureId();

            if (lecture == null) {
                log.warn("lecture is null for registerId: {}", reg.getRegisterId());
            }

            MycourseDTO dto = new MycourseDTO();
            dto.setLectureId(lecture.getLectureId());
            dto.setSubjectName(lecture.getSubjectName());
            dto.setDepartment(lecture.getDepartment());
            return dto;
        }).toList();
    }

    @Override
    public List<LectureWeekDTO> getWeeksByLectureId(Integer lectureId) {
        return lectureWeekRepository.findByLecture_LectureId(lectureId).stream()
                .map(week -> {
                    LectureWeekDTO dto = new LectureWeekDTO();
                    dto.setWeekId(week.getWeekId());
                    dto.setLectureId(week.getLecture().getLectureId());
                    dto.setWeekNumber(week.getWeekNumber());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<LectureContentDTO> getContentsByWeekId(Integer weekId) {
        return lectureContentRepository.findByWeek_WeekId(weekId).stream()
                .map(content -> {
                    LectureContentDTO dto = new LectureContentDTO();
                    dto.setLectureManagementId(content.getLectureManagementId());
                    dto.setChapterName(content.getChapterName());
                    dto.setOrderName(content.getOrderName());
                    dto.setYoutubeVideoId(content.getYoutubeVideoId());
                    dto.setVideoDuration(content.getVideoDuration());

                    // 파일 정보 추가
                    if (content.getFile() != null) {
                        dto.setFileId(content.getFile().getFileId());
                        dto.setFileName(content.getFile().getFileName());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getAssignmentsByLectureId(Integer lectureId) {
        return lectureAssignmentRepository.findByLecture_LectureId(lectureId).stream()
                .map(assignment -> {
                    AssignmentDTO dto = new AssignmentDTO();
                    dto.setAssignmentId(assignment.getAssignmentId());
                    dto.setTitle(assignment.getTitle());
                    dto.setDescription(assignment.getDescription());
                    dto.setStartDatetime(assignment.getStartDatetime());
                    dto.setEndDatetime(assignment.getEndDatetime());
                    dto.setSubmissionCount(assignment.getSubmissionCount());
                    dto.setLectureId(assignment.getLecture().getLectureId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentSubmitDTO getSubmitStatus(Integer assignmentId, Integer stdtId) {
        Optional<AssignmentSubmit> submitOpt = assignmentSubmitRepository.findByAssignment_AssignmentIdAndStudent_StdtId(assignmentId, stdtId);

        AssignmentSubmitDTO dto = new AssignmentSubmitDTO();

        if (submitOpt.isPresent()) {
            AssignmentSubmit submit = submitOpt.get();
            dto.setSubmitted(true);
            dto.setScore(submit.getScore());
            dto.setSubmissionType(submit.getSubmissionType());
            dto.setSubmissionDate(submit.getSubmissionDate());
        } else {
            dto.setSubmitted(false);
        }

        return dto;
    }

    @Override
    public AssignmentDTO getAssignmentByWeekId(Integer weekId) {
        LectureAssignment assignment = lectureAssignmentRepository.findByWeek_WeekId(weekId)
                .orElseThrow(() -> new RuntimeException("해당 주차의 과제가 존재하지 않습니다."));

        AssignmentDTO dto = new AssignmentDTO();
        dto.setAssignmentId(assignment.getAssignmentId());
        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        dto.setStartDatetime(assignment.getStartDatetime());
        dto.setEndDatetime(assignment.getEndDatetime());
        return dto;
    }

    @Override
    public void saveAssignmentSubmit(MultipartFile file, Integer lectureId, Integer weekNumber, Integer stdtId) {

        // 주차 정보 가져오기
        LectureWeek week = lectureWeekRepository
                .findByLecture_LectureIdAndWeekNumber(lectureId, weekNumber)
                .orElseThrow(() -> new RuntimeException("해당 주차 정보를 찾을 수 없습니다."));

        // 과제 엔티티 찾기
        LectureAssignment assignment = lectureAssignmentRepository.findByWeek(week)
                .orElseThrow(() -> new RuntimeException("해당 주차의 과제가 없습니다."));

        // 파일 엔티티 생성 (더미)
        File dummyFile = new File();
        dummyFile.setFileId(1); // 실제 파일 저장 로직으로 대체 가능
        dummyFile.setFileName(file.getOriginalFilename());
        dummyFile.setFileSize((int) file.getSize());

        // 제출 엔티티 생성
        AssignmentSubmit submit = new AssignmentSubmit();
        submit.setAssignment(assignment);
        submit.setLecture(new LectureInfo(lectureId));
        submit.setFile(dummyFile);
        submit.setSubmissionType("온라인");
        submit.setSubmissionDate(LocalDateTime.now().toString());
        submit.setScore(null); // 아직 미채점

        // 학생 정보 설정
        submit.setStudent(new StudentInfo(stdtId));

        // 저장
        assignmentSubmitRepository.save(submit);
    }

    @Override
    public AssignmentDTO getAssignmentByLectureAndWeek(Integer lectureId, Integer weekNumber) {
        LectureWeek week = lectureWeekRepository
                .findByLecture_LectureIdAndWeekNumber(lectureId, weekNumber)
                .orElseThrow(() -> new RuntimeException("해당 주차 정보가 없습니다."));

        LectureAssignment assignment = lectureAssignmentRepository
                .findByWeek(week)
                .orElseThrow(() -> new RuntimeException("해당 주차에 과제가 없습니다."));

        AssignmentDTO dto = new AssignmentDTO();
        dto.setAssignmentId(assignment.getAssignmentId());
        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        dto.setStartDatetime(assignment.getStartDatetime());
        dto.setEndDatetime(assignment.getEndDatetime());
        dto.setLectureId(assignment.getLecture().getLectureId());
        return dto;
    }

    @Override
    public List<LectureContentDTO> getContentsByLectureAndWeek(Integer lectureId, Integer weekNumber) {
        LectureWeek week = lectureWeekRepository
                .findByLecture_LectureIdAndWeekNumber(lectureId, weekNumber)
                .orElseThrow(() -> new RuntimeException("해당 주차 정보가 없습니다."));

        return lectureContentRepository.findByWeek(week).stream()
                .map(content -> {
                    LectureContentDTO dto = new LectureContentDTO();
                    dto.setLectureManagementId(content.getLectureManagementId());
                    dto.setChapterName(content.getChapterName());
                    dto.setOrderName(content.getOrderName());
                    dto.setYoutubeVideoId(content.getYoutubeVideoId());
                    dto.setVideoDuration(content.getVideoDuration());

                    if (content.getFile() != null) {
                        dto.setFileId(content.getFile().getFileId());
                        dto.setFileName(content.getFile().getFileName());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }


}


