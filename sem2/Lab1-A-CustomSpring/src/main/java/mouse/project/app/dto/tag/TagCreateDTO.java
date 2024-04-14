package mouse.project.app.dto.tag;

import lombok.Data;
@Data
public class TagCreateDTO {
    private String name;
    private String colorHex;
    private Long ownerId;
}
