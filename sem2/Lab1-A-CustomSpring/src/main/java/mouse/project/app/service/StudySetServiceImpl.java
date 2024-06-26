package mouse.project.app.service;

import lombok.RequiredArgsConstructor;
import mouse.project.app.dao.StudySetRepository;
import mouse.project.app.dto.studyset.StudySetCreateDTO;
import mouse.project.app.dto.studyset.StudySetResponseDTO;
import mouse.project.app.dto.studyset.StudySetUpdateDTO;
import mouse.project.app.dto.studyset.StudySetWithTermsResponseDTO;
import mouse.project.app.mapper.StudySetMapper;
import mouse.project.app.model.StudySet;
import mouse.project.app.utils.DateUtils;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.service.container.ServiceProviderContainer;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudySetServiceImpl implements StudySetService {
    private final StudySetRepository repository;
    private final StudySetMapper studySetMapper;
    private final ServiceProviderContainer services;
    @Override
    public List<StudySetResponseDTO> findAll() {
        List<StudySetResponseDTO> to = services.crud(repository)
                .findAll()
                .to(studySetMapper::toResponse);
        System.out.println(to);
        return to;
    }

    private void assignTime(StudySet studySet) {
        LocalDateTime timeNow = DateUtils.timeNowToSeconds();
        studySet.setCreatedAt(timeNow);
    }

    @Override
    public List<StudySetResponseDTO> findAllIncludeDeleted() {
        return services.soft(repository).findAllWithDeleted().to(studySetMapper::toResponse);
    }

    @Override
    public List<StudySetResponseDTO> findAllByNameIgnoreCase(String name) {
        return services.use(repository).multi(r -> r.findAllByNameIgnoreCase(name)).to(studySetMapper::toResponse);
    }

    @Override
    public List<StudySetResponseDTO> findAllByCreatedDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return services.
                use(repository).
                multi(r -> r.findAllByCreatedDateRange(startDate, endDate)).
                to(studySetMapper::toResponse);
    }

    @Override
    public void deleteById(Long id) {
        services.crud(repository).removeById(id);
    }

    @Override
    public void restoreById(Long id) {
        services.soft(repository).restoreById(id);
    }

    @Override
    public StudySetResponseDTO save(StudySetCreateDTO dto) {
        return services.crud(repository).save(() -> {
            StudySet studySet = studySetMapper.fromCreate(dto);
            assignTime(studySet);
            return studySet;
        }).to(studySetMapper::toResponse);
    }

    @Override
    public StudySetResponseDTO update(StudySetUpdateDTO model) {
        return services.crud(repository).update(model, studySetMapper::fromUpdate).to(studySetMapper::toResponse);
    }

    @Override
    public StudySetResponseDTO findById(Long id) {
        return services.crud(repository).findById(id).orThrow(studySetMapper::toResponse);
    }

    @Override
    public StudySetResponseDTO findByIdIncludeDeleted(Long id) {
        return services.soft(repository).getByIdIncludeDeleted(id).orThrow(studySetMapper::toResponse);
    }

    @Override
    public List<StudySetResponseDTO> findStudySetsByUser(Long userId) {
        return services.use(repository).multi(r -> r.findAllByUserId(userId)).to(studySetMapper::toResponse);
    }

    @Override
    public StudySetResponseDTO saveWithCustomTime(StudySetCreateDTO createDTO, LocalDateTime customTime) {
        return services.crud(repository).save(() -> {
            StudySet studySet = studySetMapper.fromCreate(createDTO);
            studySet.setCreatedAt(DateUtils.toSeconds(customTime));
            return studySet;
        }).to(studySetMapper::toResponse);
    }

    @Override
    public StudySetWithTermsResponseDTO findByIdWithTerms(Long id) {
        return services.use(repository)
                .optional(r -> r.findAllByIdWithTerms(id))
                .orThrow(studySetMapper::toResponseWithTerms);
    }
}
