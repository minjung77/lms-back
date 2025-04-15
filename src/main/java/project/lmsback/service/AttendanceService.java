package project.lmsback.service;

import project.lmsback.domain.AttendanceRequestDto;
import project.lmsback.domain.AttendanceStatusDTO;

import java.util.List;

public interface AttendanceService {
    void markAttendance(Integer lectureId, Integer contentId, Integer stdtId);

    List<AttendanceStatusDTO> getAttendanceStatusByStudent(Integer stdtId);
}
