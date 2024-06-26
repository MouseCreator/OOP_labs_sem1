package mouse.project.app.service.optimized;

import mouse.project.app.dto.studyset.*;

import java.util.List;

public interface OptimizedStudySetService {
    StudySetWithTermsResponseDTO create(StudySetWithCreatorDTO createDTO);
    StudySetDescriptionDTO getShortDescription(Long id);
    List<StudySetDescriptionDTO> getStudySetsByUser(Long userId);
    List<StudySetDescriptionDTO> getStudySetsByUser(Long userId, Integer page, Integer size);
    StudySetDescriptionWithProgressDTO getDescription(Long id, Long userId);
    StudySetHeaderResponseDTO getHeader(Long id);


}
