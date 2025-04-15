package project.lmsback.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterCartDTO {
    private Integer stdtId;
    private Integer lectureId;
    private Integer priorityOrder;

}
