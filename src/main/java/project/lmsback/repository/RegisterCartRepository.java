package project.lmsback.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.lmsback.domain.LectureInfo;
import project.lmsback.domain.RegisterCart;
import project.lmsback.domain.RegisterCartDTO;
import project.lmsback.domain.StudentInfo;

import java.util.List;
import java.util.Optional;

public interface RegisterCartRepository extends JpaRepository<RegisterCart, Integer> {

    // 장바구니 중복 확인
    Optional<RegisterCart> findByStudentAndLecture(StudentInfo student, LectureInfo lecture);

    // 장바구니 목록 조회 (DTO 매핑)
    @Query("SELECT new project.lmsback.domain.RegisterCartDTO(c.student.stdtId, c.lecture.lectureId, c.priorityOrder) " +
            "FROM RegisterCart c WHERE c.student.stdtId = :stdtId " +
            "ORDER BY CASE WHEN c.priorityOrder IS NULL THEN 1 ELSE 0 END, c.priorityOrder ASC")
    List<RegisterCartDTO> findCartDTOByStdtId(@Param("stdtId") Integer stdtId);
}

