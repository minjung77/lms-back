package project.lmsback.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import project.lmsback.domain.LectureContent;
import project.lmsback.domain.LectureFileUploadDTO;
import project.lmsback.domain.LectureWeek;

import java.util.List;

public interface LectureContentRepository extends JpaRepository<LectureContent, Integer> {
    List<LectureContent> findByWeek_WeekId(Integer weekId);
    LectureContent findByLecture_LectureIdAndOrderName(Integer lectureId, String name);

    List<LectureContent> findByLecture_LectureId(Integer lectureId);

    List<LectureContent> findByWeek(LectureWeek week);

}
