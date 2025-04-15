
package project.lmsback.controller.professor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.lmsback.domain.*;
import project.lmsback.service.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/prof")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class pf_BoardController {

    private final LectureInfoService lectureInfoService;
    private final ProfInfoService profInfoService;
    private final StudentInfoService studentInfoService;
    private final RegisterClassService registerClassService;
    private final AssignmentSubmitService assignmentSubmitService;
    private final FileService fileService;
    private final LectureContentService lectureContentService;
    private final LectureAssingmentService lectureAssingmentService;

    @GetMapping("/myLecture")
    public ResponseEntity<?> myLecture() {
        ResponseEntity response = ResponseEntity.badRequest().build();

        //교수 번호 임의 지정
        int profid = 1;

        List<ProfLecturesDTO> list = lectureInfoService.findByLecture_profId(profid);

        if (list.size() > 0) {
            response = ResponseEntity.ok().body(list);
        }else{
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return response;
    }

    @GetMapping("/edit")
    public ResponseEntity<?> edit() {
        ResponseEntity response = ResponseEntity.badRequest().build();
        int profid = 1;
        ProfInfo dto = profInfoService.findByProfId(profid);
        log.info("dto: {} ", dto);
        if (dto == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(profInfoService.findByProfId(profid), HttpStatus.OK);
    }

    @GetMapping("/lecturelist")
    public ResponseEntity<?> lecturelist() {
        ResponseEntity response = ResponseEntity.badRequest().build();

        List<LectureListsDTO> dto = lectureInfoService.listLecture(1);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/videoUpload")
    public ResponseEntity<?> videoUpload() {
        ResponseEntity response = ResponseEntity.badRequest().build();

        List<LectureListsDTO> dtolist = lectureInfoService.lectureList(1);

        if (dtolist.size() > 0) {
            return new ResponseEntity<>(dtolist, HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/stdtlist")
    public ResponseEntity<?> stdtlist() {
        ResponseEntity response = ResponseEntity.badRequest().build();

        List<StudentProfileDTO> totalists = registerClassService.classUserList(1);

        return ResponseEntity.ok().body(totalists);
    }

    //과제 평가 페이지
    @GetMapping("/stdtreport")
    public ResponseEntity<?> stdtreport(){
        ResponseEntity response = ResponseEntity.badRequest().build();

        List<AssignmentsViewDTO> list = assignmentSubmitService.assignmentViews(1);

        return ResponseEntity.ok().body(list);
    }

    //메세지 화면
    @GetMapping("/message")
    public ResponseEntity<?> message() {
        ResponseEntity response = ResponseEntity.badRequest().build();

        List<StudentProfileDTO> dtos = registerClassService.classMassageList(1001);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/editUpdate")
    public ResponseEntity<?> editUpdata(@RequestBody ProfInfo dto) {
        ResponseEntity response = ResponseEntity.badRequest().build();

        ProfInfo saved = profInfoService.uploadProf(dto);

        return ResponseEntity.ok().body(saved);
    }

    // 공지사항

    // 파일 업로드 기능
    @PostMapping("/videoUploadInsert")
    public ResponseEntity<?> videoUploadInsert(LectureFileUploadDTO dto, @RequestParam List<MultipartFile> file) {
        ResponseEntity response = ResponseEntity.badRequest().build();

        File fileDTO = fileService.saveUploadFile(file);
        lectureContentService.lectureUpdateFile(dto, fileDTO);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/assignmentInsert")
    public ResponseEntity<?> assignment(AssignmentUploadDTO dto, @RequestParam MultipartFile file) {
        ResponseEntity response = ResponseEntity.badRequest().build();

        LectureAssignment assignment = lectureAssingmentService.assignmentUpload(dto, file);

        return ResponseEntity.ok().body(assignment);
    }

    @GetMapping("/assignmentList")
    public ResponseEntity<?> assignmentList() {
        ResponseEntity response = ResponseEntity.badRequest().build();

        List<AssignmentDTO> lists = lectureAssingmentService.findByLectureAssignment(1);

        return ResponseEntity.ok().body(lists);
    }

    // 과제 점수 기입 기능
    @PostMapping("/evaluate")
    public ResponseEntity<?> evaluate(@RequestBody EvaluateUpdateDTO assignmentSubmit) {

        if(assignmentSubmitService.evaluateUpdate(assignmentSubmit) ) {
            //생성한 토큰을 json 형식으로 만듦.
            Map<String, String> tokens = Map.of(
                    "msg", "success"
            );
            return ResponseEntity.ok().body(tokens);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("성적 점수 수정 실패");
        }
    }

    @GetMapping("/down/{uuid}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String uuid) {
        File file = fileService.downFile_uuid(uuid);
        try {
            Path path = Path.of(file.getFilePath());
            byte[] fileBytes = java.nio.file.Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename(file.getFileName())
                    .build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileBytes);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    // 메세지 보내기 기능
}

