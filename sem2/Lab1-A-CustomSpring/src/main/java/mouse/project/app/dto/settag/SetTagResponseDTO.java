package mouse.project.app.dto.settag;

import lombok.Data;

@Data
public class SetTagResponseDTO {
    private Long userId;
    private Long studySetId;
    private Long tagId;
}
