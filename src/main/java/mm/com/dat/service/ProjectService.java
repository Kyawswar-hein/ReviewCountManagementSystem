package mm.com.dat.service;

import jakarta.transaction.Transactional;
import mm.com.dat.dto.ProjectImportDto;
import mm.com.dat.entity.*;
import mm.com.dat.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepo;
    private final DetailedDesignRepository designRepo;
    private final DetailedDesignNumberRepository numberRepo;
    private final ReviewCountRepository reviewRepo;
    private final DevelopmentLanguageRepository devLangRepo;


    public ProjectService(ProjectRepository p, DetailedDesignRepository d,
                          DetailedDesignNumberRepository n, ReviewCountRepository r, DevelopmentLanguageRepository dl) {
        this.projectRepo = p;
        this.designRepo = d;
        this.numberRepo = n;
        this.reviewRepo = r;
        this.devLangRepo = dl;

    }

    public void saveImportedData(List<ProjectImportDto> dtos) {
        for (ProjectImportDto dto : dtos) {
            // 1. Save Design Numbers (PGM, JOB, Pages)
            DetailedDesignNumber savedNumber = numberRepo.save(dto.getDesignNumber());

            // 2. Save Detailed Design (Hours) and link to Step 1
            DetailedDesign design = dto.getDetailedDesign();
            design.setDetailedDesignNumberId(savedNumber.getDetailedDesignNumberId());
            DetailedDesign savedDesign = designRepo.save(design);

            // 3. Save Review Count (Only linking Detailed Design)
            ReviewCountInformation review = dto.getReviewCount();
            review.setDetailedDesignId(savedDesign.getDetailedDesignId());

            // We are NOT setting Manufacturing, UT, or IT IDs here
            ReviewCountInformation savedReview = reviewRepo.save(review);

            // 4. Save Project and link to Review
            Project project = dto.getProject();
            project.setReviewCountId(savedReview.getReviewCountId());
            Project savedProject = projectRepo.save(project);
            projectRepo.save(project);

            if (dto.getLanguages() != null) {
                for (String langName : dto.getLanguages()) {
                    DevelopmentLanguage langEntity = new DevelopmentLanguage();
                    langEntity.setDevelopmentLanguageName(langName);
                    // Link to the unique Project ID we just generated
                    langEntity.setProjectId(savedProject.getProjectId());
                    devLangRepo.save(langEntity);
                }
            }

        }
    }
}