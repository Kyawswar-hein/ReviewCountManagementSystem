package mm.com.dat.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_development_language", schema = "dat")
@Data
public class DevelopmentLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database handles auto-increment
    @Column(name = "development_language_id")
    private Long developmentLanguageId;

    @Column(name = "development_language_name", length = 50)
    private String developmentLanguageName;

    @Column(name = "project_id", nullable = false)
    private Long projectId; // This links back to tbl_project
}
