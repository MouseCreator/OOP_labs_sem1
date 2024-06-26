package mouse.project.app.service.optimized;

import mouse.project.app.dto.progress.TermProgressUpdates;
import mouse.project.app.dto.term.TermWithProgressResponseDTO;

import java.util.List;

public interface OptimizedTermService {
    List<TermWithProgressResponseDTO> updateAll(TermProgressUpdates updates);
    List<TermWithProgressResponseDTO> getForUserFromStudySet(Long userId, Long studySetId);
    List<TermWithProgressResponseDTO> initializeProgress(Long userId, Long studySetId);
    List<TermWithProgressResponseDTO> resetProgress(Long userId, Long studySetId);
    void removeProgress(Long userId, Long studySetId);

    int getUserProgress(Long userId, Long studySetId);
}
