package project.lmsback.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LECTURE_INFO")
@ToString
@Data
public class LectureInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LECTURE_ID")
    private Integer lectureId;

    @Column(name = "CLASS_YEAR")
    private String classYear;

    @Column(name = "SEMESTER_CD")
    private Integer semesterCd;

    @Column(name = "COURSE_TYPE")
    private String courseType;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "SUBJECT_CODE")
    private String subjectCode;

    @Column(name = "SUBJECT_NAME")
    private String subjectName;

    @Column(name = "SUBJECT_LEVEL")
    private String subjectLevel;

    @Column(name = "CREDIT")
    private String credit;

    @Column(name = "MAX_CAPACITY")
    private String maxCapacity;

    @Column(name = "TIMETABLE")
    private String timetable;

    @Column(name = "START_DATE")
    private String startDate;

    @Column(name = "END_DATE")
    private String endDate;

    @Column(name = "MIN_CAPACITY")
    private String minCapacity;

    @Column(name = "GRADE_LEVEL")
    private String gradeLevel;

    @Column(name = "SUBJECT_PLAN")
    private String subjectPlan;

    @ManyToOne
    @JoinColumn(name = "PROF_ID")
    private ProfInfo professor;

    @ManyToOne
    @JoinColumn(name = "FILE_ID")
    private File file;

    @Column(name = "EVALUATOR_A")
    private Integer evaluatorA;

    @Column(name = "EVALUATOR_B")
    private Integer evaluatorB;

    // ✅ 기본 생성자
    public LectureInfo() {
    }

    // ✅ ID만 받는 생성자
    public LectureInfo(Integer lectureId) {
        this.lectureId = lectureId;
    }
}
