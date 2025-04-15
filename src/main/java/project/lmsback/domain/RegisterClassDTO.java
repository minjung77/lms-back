package project.lmsback.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClassDTO {

    //private int registerId;    // 수강신청번호 (생략 가능)
    private int stdtId;     // 학생번호
    private int lectureId;     // 개설강좌번호
//
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime applyDate;      // 신청일시

    private LocalDate applyDate; //  수정됨

}
