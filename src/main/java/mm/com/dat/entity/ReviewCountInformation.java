package mm.com.dat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_review_count_information", schema = "dat")
@Getter
@Setter
public class ReviewCountInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_count_id")
    private Long reviewCountId;

    @Column(name = "missing_information")
    private Double missingInformation;

    @Column(name = "inconsistent_information")
    private Double inconsistentInformation;

    @Column(name = "unclear_information")
    private Double unclearInformation;

    @Column(name = "input_error")
    private Double inputError;

    @Column(name = "rule_violation")
    private Double ruleViolation;

    @Column(name = "request_or_question")
    private Double requestOrQuestion;

    @Column(name = "mistake_in_pointing_out")
    private Double mistakeInPointingOut;

    @Column(name = "logic_omission_or_error")
    private Double logicOmissionOrError;

    @Column(name = "detailed_design_id")
    private Long detailedDesignId;

    public void setDetailedDesignId(Long detailedDesignId) {
        this.detailedDesignId = detailedDesignId;
    }





}
