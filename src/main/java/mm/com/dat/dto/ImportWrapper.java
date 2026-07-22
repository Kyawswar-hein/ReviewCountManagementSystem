package mm.com.dat.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ImportWrapper {
    private List<ProjectImportDto> importItems;
}