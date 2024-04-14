package mouse.project.app.service;

import mouse.project.app.dto.term.TermCreateDTO;
import mouse.project.app.dto.term.TermResponseDTO;
import mouse.project.app.dto.term.TermUpdateDTO;

import java.util.List;

public interface TermService {
    List<TermResponseDTO> getAll();
    TermResponseDTO getById(Long id);
    TermResponseDTO save(TermCreateDTO userStudySetCreateDTO);
    TermResponseDTO update(TermUpdateDTO userStudySetCreateDTO);
    void removeById(Long id);
    List<TermResponseDTO> getAllWithDeleted();
    void restoreById(Long term);
}
