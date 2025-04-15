package project.lmsback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.lmsback.domain.EnrolledCourseDTO;
import project.lmsback.domain.LectureDTO;
import project.lmsback.domain.LectureInfo;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<LectureInfo, Integer> {

    @Query("select new project.lmsback.domain.LectureDTO(lectureId, courseType, department, subjectCode, subjectName, subjectLevel, credit, timetable) from LectureInfo")
    List<LectureDTO> findLectureInfo();

    @Query("SELECT new project.lmsback.domain.LectureDTO(" +
            "l.lectureId, l.courseType, l.department, l.subjectCode, l.subjectName, l.subjectLevel, l.credit, l.timetable) " +
            "FROM LectureInfo l WHERE l.subjectName LIKE %:subjectName%")
    List<LectureDTO> findBySubjectName(@Param("subjectName") String subjectName);

    @Query("SELECT new project.lmsback.domain.LectureDTO(" +
            "l.lectureId, l.courseType, l.department, l.subjectCode, l.subjectName, l.subjectLevel, l.credit, l.timetable) " +
            "FROM LectureInfo l WHERE l.courseType LIKE %:courseType%")
    List<LectureDTO> findByCourseType(@Param("courseType") String courseType);

    @Query("SELECT new project.lmsback.domain.LectureDTO(" +
            "l.lectureId, l.courseType, l.department, l.subjectCode, l.subjectName, l.subjectLevel, l.credit, l.timetable) " +
            "FROM LectureInfo l WHERE l.department LIKE %:department%")
    List<LectureDTO> findByDepartment(@Param("department") String department);



    @Query("SELECT new project.lmsback.domain.LectureDTO(" +
            "l.lectureId, l.courseType, l.department, l.subjectCode, l.subjectName, l.subjectLevel, l.credit, l.timetable) " +
            "FROM LectureInfo l WHERE l.subjectCode LIKE %:subjectCode%")
    List<LectureDTO> findBySubjectCode(@Param("subjectCode") String subjectCode);

    // 학생 아이디로 수강신청하기
    Optional<LectureInfo> findByLectureId(Integer lectureId);


}

