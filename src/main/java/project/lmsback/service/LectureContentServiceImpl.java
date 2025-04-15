package project.lmsback.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.lmsback.domain.File;
import project.lmsback.domain.LectureContent;
import project.lmsback.domain.LectureFileUploadDTO;
import project.lmsback.repository.LectureContentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureContentServiceImpl implements LectureContentService {
    private final LectureContentRepository lectureContentRepository;

    @Override
    public LectureContent lectureUpdateFile(LectureFileUploadDTO dto, File fileDTO) {
        LectureContent cont = lectureContentRepository.findByLecture_LectureIdAndOrderName(dto.getLecture_id(), dto.getChapter());

        cont.setYoutubeVideoId(dto.getVideoId());
        cont.setFile(fileDTO);

        lectureContentRepository.save(cont);

        return cont;
    }

    @Override
    public List<LectureContent> findLectureId(Integer lectureId) {
        return lectureContentRepository.findByLecture_LectureId(lectureId);
    }
}
