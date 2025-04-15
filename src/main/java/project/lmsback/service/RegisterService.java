package project.lmsback.service;

import project.lmsback.domain.AcademicRecordDTO;
import project.lmsback.domain.EnrolledCourseDTO;
import project.lmsback.domain.RegisterCartDTO;
import project.lmsback.domain.RegisterClassDTO;

import java.util.List;
import java.util.Map;

public interface RegisterService {

    void register(RegisterClassDTO registerClassDTO);

    List<EnrolledCourseDTO> getEnrolledCoursesByStdtId(Integer stdtId);

    void deleteEnrollment(Integer stdtId, Integer lectureId);

    Map<String, Object> getSemesterGrade(Integer stdtId, String courseYear, Integer semesterCd);

}
