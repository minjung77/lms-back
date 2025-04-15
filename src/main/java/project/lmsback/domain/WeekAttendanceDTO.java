package project.lmsback.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekAttendanceDTO {

    private int weekNumber;
    private String weekTitle;
    private String status;
}
