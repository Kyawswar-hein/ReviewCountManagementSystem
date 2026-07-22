package mm.com.dat.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "tbl_project_level", schema = "dat")

public class ProjectLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_level_id")
    private Long projectLvlId;

}
