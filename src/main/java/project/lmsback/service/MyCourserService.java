package project.lmsback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.lmsback.domain.*;
import project.lmsback.repository.LectureContentRepository;
import project.lmsback.repository.LectureWeekRepository;
import project.lmsback.repository.RegisterClassRepository;

import java.util.List;

public interface MyCourserService {
    List<MycourseDTO> getCoursesByStudentId(Integer stdtId);
    List<LectureWeekDTO> getWeeksByLectureId(Integer lectureId);
    List<LectureContentDTO> getContentsByWeekId(Integer weekId);
    List<AssignmentDTO> getAssignmentsByLectureId(Integer weekId);
    AssignmentSubmitDTO getSubmitStatus(Integer assignmentId, Integer stdtId);
    AssignmentDTO getAssignmentByWeekId(Integer weekId);
    void saveAssignmentSubmit(MultipartFile file, Integer lectureId, Integer weekNumber, Integer stdtId);


    AssignmentDTO getAssignmentByLectureAndWeek(Integer lectureId, Integer weekNumber);

    List<LectureContentDTO> getContentsByLectureAndWeek(Integer lectureId, Integer weekNumber);

}