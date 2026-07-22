package mm.com.dat.controller;

import mm.com.dat.dto.ImportWrapper;
import mm.com.dat.dto.ProjectImportDto;
import mm.com.dat.service.ExcelDateImporter;
import mm.com.dat.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class ProjectUIController {

    private final ExcelDateImporter excelDateImporter;
    private final ProjectService projectService; // Use Service to handle the 4-table save chain

    public ProjectUIController(ExcelDateImporter excelDateImporter, ProjectService projectService) {
        this.excelDateImporter = excelDateImporter;
        this.projectService = projectService;
    }

    @GetMapping("/projects/import")
    public String showImportPage() {
        return "project-import";
    }

    @PostMapping("/projects/preview")
    public String preview(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        // 1. Extract the complex DTO list
        List<ProjectImportDto> dtos = excelDateImporter.extractDataForPreview(file.getInputStream());

        // 2. Wrap for proper binding to hidden HTML fields
        ImportWrapper wrapper = new ImportWrapper();
        wrapper.setImportItems(dtos);

        model.addAttribute("wrapper", wrapper);
        model.addAttribute("projects", dtos);

        return "project-import";
    }

    @PostMapping("/projects/save")
    public String save(@ModelAttribute("wrapper") ImportWrapper wrapper) {
        // 3. Check for null items before saving
        if (wrapper != null && wrapper.getImportItems() != null) {
            // 4. Use the Service to handle foreign keys and placeholder records
            projectService.saveImportedData(wrapper.getImportItems());
        }
        return "redirect:/projects/import?success";
    }
}