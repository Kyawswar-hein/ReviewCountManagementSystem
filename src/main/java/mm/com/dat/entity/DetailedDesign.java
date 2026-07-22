package mm.com.dat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_detailed_design", schema = "dat")
@Getter
@Setter
public class DetailedDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailed_design_id")
    private Long detailedDesignId;

    @Column(name = "estimated_man_hour")
    private Double estimatedManHour;

    @Column(name = "actual_man_hour")
    private Double actualManHour;

    @Column(name = "internal_complaint")
    private Double internalComplaint;

    @Column(name = "external_criticism")
    private Double externalCriticism;


    @Column(name = "detailed_design_number_id", unique = true, nullable = false)
    private Long detailedDesignNumberId;


    // --- Manual Setter if not using Lombok ---
    public void setDetailedDesignNumberId(Long detailedDesignNumberId) {
        this.detailedDesignNumberId = detailedDesignNumberId;
    }


}
