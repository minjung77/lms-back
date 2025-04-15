package project.lmsback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.lmsback.domain.LectureInfo;
import project.lmsback.domain.AssignmentDTO;
import project.lmsback.domain.LectureContent;
import project.lmsback.domain.LectureWeek;

import java.util.List;
import java.util.Optional;

public interface LectureWeekRepository extends JpaRepository<LectureWeek, Integer> {
    List<LectureWeek> findByLecture_LectureId(Integer lectureId);
    LectureWeek findByLectureAndWeekId(LectureInfo lecture, Integer weekId);

    Optional<LectureWeek> findByLecture_LectureIdAndWeekNumber(Integer lectureId, Integer weekNumber);
}
