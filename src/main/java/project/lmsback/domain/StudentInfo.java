package project.lmsback.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "stdt_info")
@ToString
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stdt_id")
    private Integer stdtId;

    @Column(name = "addr")
    private String addr;

    @Column(name = "addr_dtl")
    private String addrDtl;

    @Column(name = "email")
    private String email;

    @Column(name = "entry_year")
    private String entryYear;

    @Column(name = "gen_cd")
    private Integer genCd;

    @Column(name = "hp_no")
    private String hpNo;

    @Column(name = "major")
    private String major;

    @Column(name = "password")
    private String password;

    @Column(name = "state_cd")
    private Integer stateCd;

    @Column(name = "stdt_birth")
    private String stdtBirth;

    @Column(name = "stdt_name")
    private String stdtName;

    @Column(name = "zip")
    private String zip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public StudentInfo(Integer stdtId) {
        this.stdtId = stdtId;
    }

}
