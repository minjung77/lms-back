package project.lmsback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.lmsback.domain.*;
import project.lmsback.repository.LectureRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    @Override
    public List<LectureDTO> readLecture() {
        List<LectureDTO> lectures = lectureRepository.findLectureInfo();

        return lectures;
    }

    @Override
    public List<LectureDTO> findBySubjectName(String subjectName) {
        List<LectureDTO> lectures = lectureRepository.findBySubjectName(subjectName);

        return lectures;
    }

    @Override
    public List<LectureDTO> findByCourseType(String courseType) {
        List<LectureDTO> lectures = lectureRepository.findByCourseType(courseType);

        return lectures;
    }

    @Override
    public List<LectureDTO> findByDepartment(String department) {
        List<LectureDTO> lectures = lectureRepository.findByDepartment(department);

        return lectures;
    }

    @Override
    public List<LectureDTO> findBySubjectCode(String subjectCode) {
        List<LectureDTO> lectures = lectureRepository.findBySubjectCode(subjectCode);

        return lectures;
    }




//    @Override
//    public List<LectureDTO> findLecture(String findtype, String findkey) {
//        List<LectureDTO> lectures = null;
//
//
//        switch (findtype) {
//            case "course_type":
//                lectures = lectureRepository.findByCourseTypeContains(findkey); break;
//            case "department":
//                lectures = lectureRepository.findByDepartmentContains(findkey); break;
//            case "subject_code":
//                lectures = lectureRepository.findBySubjectCodeContains(findkey); break;
//            case "subject_name":
//                lectures = lectureRepository.findBySubjectNameContains(findkey); break;
//        }
//        return lectures;
//    }

//    @Override
//    public List<LectureDTO> findAllLectures() {
//        return lectureRepository.findLectureInfo();
//    }
//
//    @Override
//    public List<LectureDTO> findBySubjectName(String subjectName) {
//        return lectureRepository.findBySubjectName(subjectName);
//    }
//
//    @Override
//    public List<LectureDTO> findByCourseType(String courseType) {
//        return lectureRepository.findByCourseType(courseType);
//    }
//
//    @Override
//    public List<LectureDTO> findByDepartment(String department) {
//        return lectureRepository.findByDepartment(department);
//    }
//
//    @Override
//    public List<LectureDTO> findBySubjectCode(String subjectCode) {
//        return lectureRepository.findBySubjectCode(subjectCode);
//    }



}
