package mouse.project.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStudySetModel {
    private Long userId;
    private Long setId;
    private String type;
}
