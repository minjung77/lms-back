package project.lmsback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.lmsback.domain.*;
import project.lmsback.repository.LectureContentRepository;
import project.lmsback.repository.LectureInfoRepository;
import project.lmsback.repository.LectureWeekRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LectureInfoServiceImpl implements LectureInfoService {

    private final LectureInfoRepository lectureInfoRepository;
    private final LectureWeekRepository lectureWeekRepository;//week 테이블 포함되있음
    private final LectureContentRepository lectureContentRepository;//file 테이블 포함되있음
    private final LectureContentService lectureContentService;
    private final FileService fileService;

    @Override
    public List<ProfLecturesDTO> findByLecture_profId(Integer profId) {
        List<LectureInfo> list = lectureInfoRepository.findByProfessor_ProfId(profId);
        List<ProfLecturesDTO> profLectures = new ArrayList<>();
        list.forEach(lectureInfo -> {
            ProfLecturesDTO dto = ProfLecturesDTO.builder()
                    .lectureId(lectureInfo.getLectureId())
                    .semesterCd(lectureInfo.getSemesterCd())
                    .department(lectureInfo.getDepartment())
                    .gradeLevel(lectureInfo.getGradeLevel())
                    .subjectName(lectureInfo.getSubjectName())
                    .subjectLevel(lectureInfo.getSubjectLevel())
                    .profId(lectureInfo.getProfessor().getProfId())
                    .build();
            profLectures.add(dto);
        });
        return profLectures;
    }

    @Override
    public List<LectureListsDTO> lectureList(Integer profId) {
        List<LectureInfo> lectures = lectureInfoRepository.findByProfessor_ProfId(profId);
        List<LectureListsDTO> dtoList = new ArrayList<>();

        for (LectureInfo lecture : lectures) {//개설강의 수만큼 반복//1001, 1003
            List<LectureWeek> weeks = lectureWeekRepository.findByLecture_LectureId(lecture.getLectureId());
            List<LectureContent> contents = lectureContentRepository.findByLecture_LectureId(lecture.getLectureId());

            for (LectureWeek week : weeks) {
                for (LectureContent content : contents) {
                    log.info("content - lectureid : {} ", content.getLecture().getLectureId());
                    if (content.getWeek().getWeekId().equals(week.getWeekNumber())) {
                        log.info(">> content >> {}", content);
                        LectureListsDTO dto = LectureListsDTO.builder()
                                .lectureId(lecture.getLectureId())
                                .weekNumber(week.getWeekNumber())
                                .order_name(content.getOrderName())
                                .file_id(content.getFile().getFileId())
                                .file_name(content.getFile().getFileName())
                                .uuid(content.getFile().getUuid())
                                .subject_name(lecture.getSubjectName())
                                .subject_plan(lecture.getSubjectPlan())
                                .semester_cd(lecture.getSemesterCd())
                                .build();
                        dtoList.add(dto);
                    }
                }
            }
        }

        return dtoList;
    }

    @Override
    public List<LectureListsDTO> listLecture(Integer profId) {
        List<LectureListsDTO> dtos = new ArrayList<>();

        lectureInfoRepository.findByProfessor_ProfId(profId).forEach(lectureInfo -> {

            List<LectureContent> contents = lectureContentService.findLectureId(lectureInfo.getLectureId());

            contents.forEach(lectureContent -> {

                LectureWeek lectureWeek = lectureContent.getWeek();
                File file = fileService.getFileById(lectureContent.getFile().getFileId());

                LectureListsDTO dto = LectureListsDTO.builder()
                        .lectureId(lectureInfo.getLectureId())
                        .weekNumber(lectureWeek.getWeekNumber())
                        .order_name(lectureContent.getOrderName())
                        .file_id(file.getFileId())
                        .file_name(file.getFileName())
                        .uuid(file.getUuid())
                        .subject_name(lectureInfo.getSubjectName())
                        .subject_plan(lectureInfo.getSubjectPlan())
                        .semester_cd(lectureInfo.getSemesterCd())
                        .build();

                dtos.add(dto);
            });

        });

        return dtos;
    }

    @Override
    public LectureInfo findByLectureId(Integer lectureId) {
        return lectureInfoRepository.findByLectureId(lectureId);
    }

}
