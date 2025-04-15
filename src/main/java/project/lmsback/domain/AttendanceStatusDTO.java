package project.lmsback.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceStatusDTO {
    private Integer lectureId;
    private String courseName;
    private String instructor;
    private int attendanceRate;
    private List<WeekAttendanceDTO> weeklyStatus;
}
