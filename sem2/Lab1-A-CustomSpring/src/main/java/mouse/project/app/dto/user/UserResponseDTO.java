package mouse.project.app.dto.user;


import lombok.Data;


@Data
public class UserResponseDTO  {
    private Long id;
    private String name;
    private String profilePictureUrl;
}
