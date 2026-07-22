package mm.com.dat.service;

import mm.com.dat.dto.ProjectImportDto;
import mm.com.dat.entity.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelDateImporter {

    public List<ProjectImportDto> extractDataForPreview(InputStream excelFile) throws Exception {
        List<ProjectImportDto> previewList = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(excelFile);
        // Required to read calculated values from SUM() formulas
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Sheet sheet = workbook.getSheet("収集数値");

        if (sheet == null) throw new RuntimeException("Sheet '収集数値' not found!");

        Map<String, Integer> colMap = scanHeaders(sheet);

        // Data starts at Row 10 (Index 9) or Row 13 (Index 12)
        int dataStartRow = 9;

        String lastTeam = "", lastName = "";
        String[] allLangs = {"ASM", "COBOL", "C/C++", "Java", "JSP", "Python", "VBA", "JCL", "Shell", "Angular", "その他"};

        for (int i = dataStartRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || isRowEmpty(row)) continue;

            ProjectImportDto dto = new ProjectImportDto();
            Project project = new Project();

            // 1. Project Info - Using getSafeCell to prevent index -1 errors
            String currentTeam = getCellValueAsString(getSafeCell(row, colMap.get("Team")), evaluator);
            String currentProject = getCellValueAsString(getSafeCell(row, colMap.get("Project_Name")), evaluator);

            // Handle merged cells
            if (!currentTeam.isEmpty()) lastTeam = currentTeam;
            if (!currentProject.isEmpty()) lastName = currentProject;

            project.setTeamName(lastTeam);
            project.setProjectName(lastName);
            project.setSystemName(getCellValueAsString(getSafeCell(row, colMap.get("System")), evaluator));
            project.setDifficultyLevel(getCellValueAsString(getSafeCell(row, colMap.get("Difficulty")), evaluator));
            project.setHostDistributed(getCellValueAsString(getSafeCell(row, colMap.get("Host_Dist")), evaluator));
            project.setWeekId(1L);
            dto.setProject(project);

            // 2. Languages extraction
            for (String lang : allLangs) {
                Integer colIdx = colMap.get("Lang_" + lang);
                String mark = getCellValueAsString(getSafeCell(row, colIdx), evaluator).trim();
                if (mark.equals("○") || mark.equals("◯") || mark.equals("〇") || mark.equalsIgnoreCase("O")) {
                    dto.getLanguages().add(lang);
                }
            }

            // 3. Design Numbers
            DetailedDesignNumber numbers = new DetailedDesignNumber();
            numbers.setPgmNew(getNumericValue(row, colMap.get("PGM_New"), evaluator));
            numbers.setPgmModified(getNumericValue(row, colMap.get("PGM_Modified"), evaluator));
            numbers.setJobNew(getNumericValue(row, colMap.get("JOB_New"), evaluator));
            numbers.setJobModified(getNumericValue(row, colMap.get("JOB_Modified"), evaluator));
            dto.setDesignNumber(numbers);

            // 4. Man-Hours
            DetailedDesign design = new DetailedDesign();
            design.setEstimatedManHour(getNumericValue(row, colMap.get("Est_ManHour"), evaluator));
            design.setActualManHour(getNumericValue(row, colMap.get("Act_ManHour"), evaluator));
            dto.setDetailedDesign(design);

            // 5. Review Categories
            ReviewCountInformation review = new ReviewCountInformation();
            review.setMissingInformation(getNumericValue(row, colMap.get("Missing_Info"), evaluator));
            review.setRuleViolation(getNumericValue(row, colMap.get("Rule_Violation"), evaluator));
            review.setInputError(getNumericValue(row, colMap.get("Input_Error"), evaluator));
            review.setLogicOmissionOrError(getNumericValue(row, colMap.get("Logic_Error"), evaluator));
            dto.setReviewCount(review);

            previewList.add(dto);
        }
        workbook.close();
        return previewList;
    }

    private Map<String, Integer> scanHeaders(Sheet sheet) {
        Map<String, Integer> colMap = new HashMap<>();
        Row r7 = sheet.getRow(7);
        Row r8 = sheet.getRow(8);

        if (r8 == null) return colMap;

        String currentType = "";
        String[] allLangs = {"ASM", "COBOL", "C/C++", "Java", "JSP", "Python", "VBA", "JCL", "Shell", "Angular", "その他"};

        for (int i = 0; i < r8.getLastCellNum(); i++) {
            String h7 = (r7 != null) ? getCellValueAsString(r7.getCell(i), null).trim() : "";
            String h8 = getCellValueAsString(r8.getCell(i), null).trim();

            // Robust matching for merged/complex headers
            if (h8.contains("委託元") || h8.contains("department")) colMap.put("Team", i);
            else if (h8.contains("案件名") || h8.contains("Case")) colMap.put("Project_Name", i);
            else if (h8.contains("システム名") || h8.contains("system")) colMap.put("System", i);
            else if (h8.contains("難易度") || h8.contains("Difficulty")) colMap.put("Difficulty", i);
            else if (h8.contains("ホスト") || h8.contains("host")) colMap.put("Host_Dist", i);

            for (String lang : allLangs) {
                if (h8.equalsIgnoreCase(lang)) colMap.put("Lang_" + lang, i);
            }

            if (h7.contains("新規")) currentType = "New";
            else if (h7.contains("修正")) currentType = "Mod";

            if ("New".equals(currentType)) {
                if (h8.equals("PGM")) colMap.put("PGM_New", i);
                else if (h8.equals("JOB")) colMap.put("JOB_New", i);
            } else if ("Mod".equals(currentType)) {
                if (h8.equals("PGM")) colMap.put("PGM_Modified", i);
                else if (h8.equals("JOB")) colMap.put("JOB_Modified", i);
            }

            if (h8.contains("見積工数")) colMap.put("Est_ManHour", i);
            else if (h8.contains("実績工数")) colMap.put("Act_ManHour", i);
            else if (h8.contains("記載漏れ")) colMap.put("Missing_Info", i);
            else if (h8.contains("ルール違反")) colMap.put("Rule_Violation", i);
            else if (h8.contains("入力ミス")) colMap.put("Input_Error", i);
            else if (h8.contains("ロジック漏れ")) colMap.put("Logic_Error", i);
        }
        return colMap;
    }

    private Cell getSafeCell(Row row, Integer index) {
        if (index == null || index < 0) return null;
        return row.getCell(index);
    }

    private Double getNumericValue(Row row, Integer colIndex, FormulaEvaluator evaluator) {
        Cell cell = getSafeCell(row, colIndex);
        if (cell == null) return 0.0;
        try {
            CellValue cellValue = evaluator.evaluate(cell);
            if (cellValue != null && cellValue.getCellType() == CellType.NUMERIC) {
                return cellValue.getNumberValue();
            }
        } catch (Exception e) { return 0.0; }
        return 0.0;
    }

    private String getCellValueAsString(Cell cell, FormulaEvaluator evaluator) {
        if (cell == null) return "";
        if (evaluator != null && cell.getCellType() == CellType.FORMULA) {
            try {
                CellValue cv = evaluator.evaluate(cell);
                if (cv.getCellType() == CellType.NUMERIC) return String.valueOf((long)cv.getNumberValue());
                if (cv.getCellType() == CellType.STRING) return cv.getStringValue().trim();
            } catch (Exception e) { return ""; }
        }
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue().trim();
        if (cell.getCellType() == CellType.NUMERIC) return String.valueOf((long) cell.getNumericCellValue());
        return cell.toString().trim();
    }

    private boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) return false;
        }
        return true;
    }
}