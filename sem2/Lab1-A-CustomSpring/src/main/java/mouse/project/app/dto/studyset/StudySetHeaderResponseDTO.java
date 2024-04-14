package mouse.project.app.dto.studyset;

import lombok.Data;
import mouse.project.app.dto.user.UserResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class StudySetHeaderResponseDTO {
    private Long id;
    private UserResponseDTO owner;
    private List<UserResponseDTO> savedByUsers;
    private String name;
    private String pictureUrl;
    private LocalDateTime createdAt;
}
