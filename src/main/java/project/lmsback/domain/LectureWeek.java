package project.lmsback.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "LECTURE_WEEK")
@ToString
public class LectureWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lectweekId;

    @Column(name = "WEEK_ID")
    private Integer weekId;

    @Column(name = "WEEK_NUMBER")
    private Integer weekNumber;

    @ManyToOne
    @JoinColumn(name = "LECTURE_ID")
    private LectureInfo lecture;

    // Getter/Setter
}