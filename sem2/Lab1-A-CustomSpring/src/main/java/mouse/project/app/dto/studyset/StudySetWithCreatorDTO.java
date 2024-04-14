package mouse.project.app.dto.studyset;

import lombok.Data;
import mouse.project.app.dto.term.TermCreateDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudySetWithCreatorDTO {
    private Long creatorId;
    private String name;
    private String pictureUrl;
    private List<TermCreateDTO> terms = new ArrayList<>();
}
