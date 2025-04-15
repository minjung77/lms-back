package project.lmsback.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnrolledCourseDTO {
    private Integer registerId;
    private Integer lectureId;
    private String courseType;
    private String department;
    private String subjectCode;
    private String subjectName;
    private String subjectLevel;
    private String credit;
    private String applyDate;
    private String timetable;
}