package project.lmsback.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "REGISTER_CART")
public class RegisterCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "STDT_ID")
    private StudentInfo student;

    @Column(name = "PRIORITY_ORDER")
    private Integer priorityOrder;

    @ManyToOne
    @JoinColumn(name = "LECTURE_ID")
    private LectureInfo lecture;

    // Getter/Setter
}
