package mm.com.dat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_detailed_design_number", schema = "dat")
@Getter
@Setter

public class DetailedDesignNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailed_design_number_id")
    private Long detailedDesignNumberId;

    // PGM Metrics
    @Column(name = "pgm_new")
    private Double pgmNew;

    @Column(name = "pgm_modified")
    private Double pgmModified;

    // JOB Metrics
    @Column(name = "job_new")
    private Double jobNew;

    @Column(name = "job_modified")
    private Double jobModified;

    // Pages Metrics
    @Column(name = "pages_new")
    private Double pagesNew;

    @Column(name = "pages_modified")
    private Double pagesModified;

    // D Case Metrics
    @Column(name = "dcase_n")
    private Double dcaseN;

    @Column(name = "dcase_m")
    private Double dcaseM;

    @Column(name = "detailed_design_type")
    private String detailedDesignType;
}