package project.lmsback.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.lmsback.domain.File;
import project.lmsback.domain.LectureFileUploadDTO;
import project.lmsback.repository.FileRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Value("${savePdsDir}") private String savePdsDir;

    @Override
    public File getFileById(Integer fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));
    }

    @Override
    public File saveUploadFile(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        File lastSavedFile = null;

        int commonFileOrgId = generateRandomInteger();

        for (MultipartFile multipartFile : files) {
            String originalName = multipartFile.getOriginalFilename();
            String uuid = makeUUID();
            String savedName = uuid + "_" + originalName;

            String savePath = savePdsDir + savedName;

            try {
                multipartFile.transferTo(new java.io.File(savePath));

                int fileSizeKB = (int) (multipartFile.getSize() / 1024);

                File file = new File();
                file.setFileName(originalName);
                file.setFilePath(savePath);
                file.setFileSize(fileSizeKB);
                file.setUuid(uuid);
                file.setFileOrgId(commonFileOrgId);

                lastSavedFile = fileRepository.save(file);

            } catch (IOException e) {
                log.error("파일 저장 중 오류 발생: {}", e.getMessage());
                throw new RuntimeException("파일 저장 실패");
            }
        }

        return lastSavedFile;
    }

    @Override
    public File saveOne(File file) {
        File savedFile = fileRepository.save(file);
        return savedFile;
    }

    @Override
    public File downFile_uuid(String uuid) {
        return fileRepository.findByUuid(uuid)
                .orElseThrow(() -> new NoSuchElementException("File not found for uuid: " + uuid));
    }


    private String makeUUID() {
        return java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    // 랜덤 숫자 생성
    private int generateRandomInteger() {
        return (int) (Math.random() * 1000000000);
    }


}
