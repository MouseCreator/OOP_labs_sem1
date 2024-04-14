package mouse.project.app.dto.user;

import lombok.Data;


@Data
public class UserUpdateDTO {
    private Long id;
    private String name;
    private String profilePictureUrl;
}
