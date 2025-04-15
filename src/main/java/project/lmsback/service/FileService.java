package project.lmsback.service;

import org.springframework.web.multipart.MultipartFile;
import project.lmsback.domain.File;
import project.lmsback.domain.LectureFileUploadDTO;

import java.util.List;

public interface FileService {

    File getFileById(Integer fileId);

    File saveUploadFile(List<MultipartFile> files);

    File saveOne(File file);

    File downFile_uuid(String uuid);
}
