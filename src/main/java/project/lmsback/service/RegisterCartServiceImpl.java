package project.lmsback.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.lmsback.domain.LectureInfo;
import project.lmsback.domain.RegisterCart;
import project.lmsback.domain.RegisterCartDTO;
import project.lmsback.domain.StudentInfo;
import project.lmsback.repository.LectureRepository;
import project.lmsback.repository.RegisterCartRepository;
import project.lmsback.repository.StudentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegisterCartServiceImpl implements RegisterCartService {
    private final RegisterCartRepository registerCartRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    @Override
    public void saveCartPriorities(List<RegisterCartDTO> priorityList) {
        for (RegisterCartDTO registerCartDTO : priorityList) {

            log.info("📦 DTO: stdtId={}, lectureId={}, priority={}",
                    registerCartDTO.getStdtId(), registerCartDTO.getLectureId(), registerCartDTO.getPriorityOrder());

            StudentInfo student = studentRepository.findByStdtId(registerCartDTO.getStdtId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

            LectureInfo lecture = lectureRepository.findByLectureId(registerCartDTO.getLectureId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));

            RegisterCart cart = registerCartRepository.findByStudentAndLecture(student, lecture)
                    .orElse(new RegisterCart());

            //  필요한 필드 세팅 (새로운 객체일 경우에도)
            cart.setStudent(student);
            cart.setLecture(lecture);
            cart.setPriorityOrder(registerCartDTO.getPriorityOrder());

            registerCartRepository.save(cart);
        }
    }

    @Override
    public List<RegisterCartDTO> getCartDTOListByStdtId(Integer stdtId) {
        return registerCartRepository.findCartDTOByStdtId(stdtId);
    }

}
