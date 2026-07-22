package mm.com.dat.dto;

import lombok.Getter;
import lombok.Setter;
import mm.com.dat.entity.DetailedDesign;
import mm.com.dat.entity.DetailedDesignNumber;
import mm.com.dat.entity.Project;
import mm.com.dat.entity.ReviewCountInformation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProjectImportDto {
    private Project project;
    private DetailedDesignNumber designNumber;
    private DetailedDesign detailedDesign;
    private ReviewCountInformation reviewCount;
    private List<String> languages = new ArrayList<>();
}
