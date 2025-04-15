package project.lmsback.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Data
@Entity
@Table(name = "prof_info")
public class ProfInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profId;

    @Column(name = "prof_name")
    private String profName;

    @Column(name = "gen_cd")
    private Integer genCd;

    @Column(name = "hp_no")
    private String hpNo;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "addr")
    private String addr;

    @Column(name = "addr_dtl")
    private String addrDtl;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    // Getter/Setter
}
