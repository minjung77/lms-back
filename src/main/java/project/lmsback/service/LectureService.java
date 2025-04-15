package project.lmsback.service;

import project.lmsback.domain.LectureDTO;

import java.util.List;

public interface LectureService {

    // 수강 강좌 목록 보기
    List<LectureDTO> readLecture();

    // 검색
    List<LectureDTO> findBySubjectName(String subjectName);
    List<LectureDTO> findByCourseType(String courseType);
    List<LectureDTO> findByDepartment(String department);
    List<LectureDTO> findBySubjectCode(String subjectCode);


}
