package project.lmsback.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.lmsback.domain.LectureInfo;
import project.lmsback.domain.RegisterCart;
import org.springframework.data.jpa.repository.JpaRepository;

import project.lmsback.domain.StudentInfo;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentInfo, Integer> {

    StudentInfo findByStdtId(Integer studentId);
    Optional<StudentInfo> findByStdtId(int stdtId);
}
