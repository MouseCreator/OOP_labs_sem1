package mouse.project.app.dto.studyset;

import lombok.Data;
import mouse.project.app.dto.term.TermUpdateDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudySetUpdateDTO {
    private Long id;
    private String name;
    private String pictureUrl;
    private List<TermUpdateDTO> terms = new ArrayList<>();
}
