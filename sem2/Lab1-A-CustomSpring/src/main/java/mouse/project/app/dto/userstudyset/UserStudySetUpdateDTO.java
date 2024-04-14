package mouse.project.app.dto.userstudyset;

import lombok.Data;

@Data
public class UserStudySetUpdateDTO {
    private Long userId;
    private Long studySetId;
    private String type;
}
