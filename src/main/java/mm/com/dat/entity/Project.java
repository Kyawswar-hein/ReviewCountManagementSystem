package mm.com.dat.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_project", schema = "dat")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "system_name")
    private String systemName;

    @Column(name = "difficulty_level")
    private String difficultyLevel; // For "難易度"

    @Column(name = "project_level")
    private String projectLevel; // For "Project Level"

    @Column(name = "host_distributed")
    private String hostDistributed;

    @Column(name = "project_month")
    private LocalDate projectMonth;

    @Column(name = "week_id")
    private Long weekId;

    // --- Getters and Setters ---

    public void setTeamName(String teamName) { this.teamName = teamName; }
    public String getTeamName() { return teamName; }

    public void setProjectName(String projectName) { this.projectName = projectName; }
    public String getProjectName() { return projectName; }

    public void setSystemName(String systemName) { this.systemName = systemName; }
    public String getSystemName() { return systemName; }

    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public String getDifficultyLevel() { return difficultyLevel; }

    public void setProjectLevel(String projectLevel) { this.projectLevel = projectLevel; }
    public String getProjectLevel() { return projectLevel; }

    public void setHostDistributed(String hostDistributed) { this.hostDistributed = hostDistributed; }
    public String getHostDistributed() { return hostDistributed; }

    public void setProjectMonth(LocalDate projectMonth) { this.projectMonth = projectMonth; }
    public LocalDate getProjectMonth() { return projectMonth; }

    public void setWeekId(Long weekId) { this.weekId = weekId; }
    public Long getWeekId() { return weekId; }

    @Column(name = "review_count_id", unique = true)
    private Long reviewCountId;

    // --- Manual Setter if not using Lombok ---
    public void setReviewCountId(Long reviewCountId) {
        this.reviewCountId = reviewCountId;
    }


}