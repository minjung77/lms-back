package project.lmsback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.lmsback.domain.AssignmentUploadDTO;
import project.lmsback.domain.LectureAssignment;
import project.lmsback.domain.LectureWeek;

import java.util.List;
import java.util.Optional;

public interface LectureAssignmentRepository extends JpaRepository<LectureAssignment, Integer> {
    List<LectureAssignment> findByLecture_LectureId(Integer lectureId);
    Optional<LectureAssignment> findByWeek_WeekId(Integer weekId);

    LectureAssignment save(LectureAssignment lectureAssignment);

    Optional<LectureAssignment> findByWeek(LectureWeek week);
}
