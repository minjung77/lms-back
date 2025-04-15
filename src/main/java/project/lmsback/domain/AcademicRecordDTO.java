package project.lmsback.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademicRecordDTO {


    private String subjectName;       // 과목명
    private String courseType;        // 이수구분
    private String subjectCode;       // 교과목코드
    private String creditApplied;     // 신청학점
    private String creditEarned;      // 이수학점
    private String termAvgScore;      // 학기 평균점수
    private String termAvgGrade;      // 학기 평균등급
    private String gradePercentage;   // 백분율
    private String gradeAvgScore;     // 전체 GPA
}
