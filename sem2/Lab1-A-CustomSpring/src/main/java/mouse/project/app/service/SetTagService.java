package mouse.project.app.service;


import mouse.project.app.dto.settag.SetTagCreateDTO;
import mouse.project.app.dto.settag.SetTagResponseDTO;
import mouse.project.app.dto.studyset.StudySetResponseDTO;

import java.util.List;

public interface SetTagService {
    List<SetTagResponseDTO> getAll();
    SetTagResponseDTO save(SetTagCreateDTO setTag);
    SetTagResponseDTO save(Long userId, Long setId, Long tagId);
    SetTagResponseDTO getSetTagById(Long userId, Long setId, Long tagId);
    List<StudySetResponseDTO> getStudySetsByUserAndTags(Long userId, List<Long> tagIds);
    void delete(Long userId, Long setId, Long tagId);
}
