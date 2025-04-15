package project.lmsback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.lmsback.domain.*;
import project.lmsback.repository.LectureAssignmentRepository;
import project.lmsback.repository.LectureInfoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LectureAssignmentServiceImpl implements LectureAssingmentService{

    private final LectureAssignmentRepository lectureAssignmentRepository;
    private final LectureInfoService lectureInfoService;
    private final LectureWeekService lectureWeekService;
    private final FileService fileService;

    @Value("${savePdsDir}") private String savePdsDir;

    @Transactional
    @Override
    public LectureAssignment assignmentUpload(AssignmentUploadDTO dto, MultipartFile files) {

        File fileDTO = new File();

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        int commonFileOrgId = generateRandomInteger();

        String originalName = files.getOriginalFilename();
        String uuid = makeUUID();
        String savedName = uuid + "_" + originalName;

        String savePath = savePdsDir + savedName;

        try {
            files.transferTo(new java.io.File(savePath));

            int fileSizeKB = (int) (files.getSize() / 1024);

            fileDTO.setFileName(originalName);
            fileDTO.setFilePath(savePath);
            fileDTO.setFileSize(fileSizeKB);
            fileDTO.setUuid(uuid);
            fileDTO.setFileOrgId(commonFileOrgId);

            fileService.saveOne(fileDTO);

        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("파일 저장 실패");
        }

        LectureInfo lectureInfo = lectureInfoService.findByLectureId(dto.getLecture_id());

        LectureWeek lectureWeek = lectureWeekService.findByLectureAndWeekId(lectureInfo, dto.getWeek_id());

        LectureAssignment assignment = LectureAssignment.builder()
                .description(dto.getDescription())
                .endDatetime(dto.getEndDatetime().substring(0, 10))
                .startDatetime(dto.getStartDatetime().substring(0, 10))
                .title(dto.getTitle())
                .submissionCount(0)
                .file(fileDTO)
                .lecture(lectureInfo)
                .week(lectureWeek)
                .build();

        lectureAssignmentRepository.save(assignment);

        return assignment;
    }

    @Override
    public List<AssignmentDTO> findByLectureAssignment(Integer profid) {
        List<ProfLecturesDTO> profLectures = lectureInfoService.findByLecture_profId(profid);

        List<AssignmentDTO> lists = new ArrayList<>();

        profLectures.forEach(profLecture -> {
            List<LectureAssignment> assignments = lectureAssignmentRepository.findByLecture_LectureId(profLecture.getLectureId());

            assignments.forEach(assignment -> {
                AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                        .assignmentId(assignment.getAssignmentId())
                        .title(assignment.getTitle())
                        .description(assignment.getDescription())
                        .endDatetime(assignment.getEndDatetime())
                        .startDatetime(assignment.getStartDatetime())
                        .lectureId(profLecture.getLectureId())
                        .submissionCount(assignment.getSubmissionCount())
                        .build(); // DTO 객체 빌드

                lists.add(assignmentDTO);
            });
        });
        return lists;
    }



    private String makeUUID() {
        return java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    private int generateRandomInteger() {
        return (int) (Math.random() * 1000000000);
    }
}
