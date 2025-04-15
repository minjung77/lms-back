package project.lmsback.repository;

import org.apache.ibatis.annotations.Param;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.lmsback.domain.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface RegisterClassRepository extends JpaRepository<RegisterClass, Integer> {
    // StudentInfo 엔티티와 연결된 stdtId로 수강 강좌를 조회
    List<RegisterClass> findByStdtId_StdtId(Integer stdtId);


    List<RegisterClass> findByLectureId_LectureId(Integer lectureId);

    @Query("SELECT new project.lmsback.domain.EnrolledCourseDTO(" +
            "r.registerId, l.lectureId, l.courseType, l.department, l.subjectCode, l.subjectName, l.subjectLevel, l.credit, r.applyDate, l.timetable) " +
            "FROM RegisterClass r JOIN r.lectureId l " +
            "WHERE r.stdtId.stdtId = :stdtId")
    List<EnrolledCourseDTO> getEnrolledCoursesByStdtId(@Param("stdtId") Integer stdtId);



    @Transactional
    @Modifying
    @Query("DELETE FROM RegisterClass rc WHERE rc.stdtId.stdtId = :stdtId AND rc.lectureId.lectureId = :lectureId")
    void deleteByStdtIdAndLectureId(@Param("stdtId") Integer stdtId,
                                    @Param("lectureId") Integer lectureId);

    boolean existsByStdtId_StdtIdAndLectureId_LectureId(Integer stdtId, Integer lectureId);


    Optional<RegisterClass> findByLectureId_LectureIdAndStdtId_StdtId(Integer lectureId, Integer stdtId);

}
