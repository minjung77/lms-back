package project.lmsback.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureDTO {

    private int lectureId; //개설강좌번호
    private String courseType; // 이수구분
    private String department; // 개설전공학과
    private String subjectCode; // 교과목코드
    private String subjectName; // 교과목명
    private String subjectLevel; //교과목 수준
    private String credit;      // 학점
    private String timetable; // 시간표
}


