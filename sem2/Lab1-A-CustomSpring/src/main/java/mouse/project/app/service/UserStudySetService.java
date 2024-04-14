package mouse.project.app.service;

import mouse.project.app.dto.user.UserResponseDTO;
import mouse.project.app.dto.user.UserWithRelation;
import mouse.project.app.dto.userstudyset.UserStudySetCreateDTO;
import mouse.project.app.dto.userstudyset.UserStudySetResponseDTO;
import mouse.project.app.dto.userstudyset.UserStudySetUpdateDTO;

import java.util.List;

public interface UserStudySetService {
    UserStudySetResponseDTO save(UserStudySetCreateDTO userStudySetCreateDTO);
    UserStudySetResponseDTO update(Long userId, Long studySetId, String relation);
    UserStudySetResponseDTO update(UserStudySetUpdateDTO userStudySetCreateDTO);
    UserStudySetResponseDTO getByUserAndStudySet(Long userId, Long studySetId);
    void removeByUserAndStudySet(Long userId, Long studySetId);
    List<UserStudySetResponseDTO> getAll();
    UserStudySetResponseDTO save(Long userId, Long studySetId, String type);
    UserResponseDTO getOwnerOfStudySet(Long id);
    List<UserWithRelation> getUsersByStudySet(Long id);
}
