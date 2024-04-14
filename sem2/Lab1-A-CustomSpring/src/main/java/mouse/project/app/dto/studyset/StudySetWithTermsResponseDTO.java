package mouse.project.app.dto.studyset;

import lombok.Data;
import mouse.project.app.dto.term.TermResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class StudySetWithTermsResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private String pictureUrl;
    private List<TermResponseDTO> terms;
}
