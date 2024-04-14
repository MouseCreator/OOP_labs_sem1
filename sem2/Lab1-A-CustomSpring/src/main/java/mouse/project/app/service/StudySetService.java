package mouse.project.app.service;


import mouse.project.app.dto.studyset.StudySetCreateDTO;
import mouse.project.app.dto.studyset.StudySetResponseDTO;
import mouse.project.app.dto.studyset.StudySetUpdateDTO;
import mouse.project.app.dto.studyset.StudySetWithTermsResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface StudySetService {
    List<StudySetResponseDTO> findAll();
    List<StudySetResponseDTO> findAllIncludeDeleted();
    List<StudySetResponseDTO> findAllByNameIgnoreCase(String name);
    List<StudySetResponseDTO> findAllByCreatedDateRange(LocalDateTime startDate, LocalDateTime endDate);
    void deleteById(Long id);
    void restoreById(Long id);
    StudySetResponseDTO save(StudySetCreateDTO model);
    StudySetResponseDTO update(StudySetUpdateDTO model);
    StudySetResponseDTO findById(Long id);
    StudySetResponseDTO findByIdIncludeDeleted(Long id);
    List<StudySetResponseDTO> findStudySetsByUser(Long userId);
    StudySetResponseDTO saveWithCustomTime(StudySetCreateDTO createDTO, LocalDateTime customTime);
    StudySetWithTermsResponseDTO findByIdWithTerms(Long id);
}
