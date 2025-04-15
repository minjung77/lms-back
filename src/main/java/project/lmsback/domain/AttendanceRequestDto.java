package project.lmsback.domain;

import lombok.Data;

@Data
public class AttendanceRequestDto {

    private Integer lectureId;
    private Integer contentId;

}
