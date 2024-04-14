package mouse.project.app.dto.userstudyset;

import lombok.Data;

@Data
public class UserStudySetCreateDTO {
    private Long userId;
    private Long studySetId;
    private String type;
}
