package project.lmsback.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.lmsback.domain.AcademicRecord;
import project.lmsback.domain.AcademicRecordDTO;
import project.lmsback.domain.StudentInfo;

import java.util.List;

public interface AcademicRecordRepository extends JpaRepository<AcademicRecord, Integer> {
    @Query("SELECT new project.lmsback.domain.AcademicRecordDTO(" +
            "l.subjectName, l.courseType, l.subjectCode, " +
            "a.creditApplied, a.creditEarned, a.termAvgScore, a.termAvgGrade, " +
            "a.gradePercentage, a.gradeAvgScore) " +
            "FROM AcademicRecord a " +
            "JOIN a.lecture l " +
            "WHERE a.courseYear = :courseYear AND a.semesterCd = :semesterCd " +
            "AND l.lectureId IN (" +
            "  SELECT r.lectureId.lectureId FROM RegisterClass r WHERE r.stdtId.stdtId = :stdtId" +
            ")")
    List<AcademicRecordDTO> findGradesByStudentAndSemester(
            @Param("stdtId") Integer stdtId,
            @Param("courseYear") String courseYear,
            @Param("semesterCd") Integer semesterCd);


}

