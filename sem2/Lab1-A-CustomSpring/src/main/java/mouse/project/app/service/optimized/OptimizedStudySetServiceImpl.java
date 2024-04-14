package mouse.project.app.service.optimized;

import jakarta.persistence.EntityNotFoundException;
import mouse.project.lib.data.page.Page;
import mouse.project.lib.data.page.PageDescription;
import mouse.project.lib.data.page.PageFactory;
import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.app.defines.UserStudySetRelation;
import mouse.project.app.dto.studyset.*;
import mouse.project.app.dto.user.UserResponseDTO;
import mouse.project.app.dto.user.UserWithRelation;
import mouse.project.app.exception.EntityStateException;
import mouse.project.app.exception.MissingEntityException;
import mouse.project.lib.service.container.ServiceProviderContainer;
import mouse.project.app.mapper.StudySetMapper;
import mouse.project.app.model.SizedStudySet;
import mouse.project.app.model.StudySet;
import mouse.project.app.dao.StudySetRepository;
import mouse.project.app.service.UserStudySetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OptimizedStudySetServiceImpl implements OptimizedStudySetService {

    private final StudySetRepository repository;
    private final StudySetMapper studySetMapper;
    private final ServiceProviderContainer services;
    private final UserStudySetService userStudySetService;
    private final OptimizedTermService optimizedTermService;
    private final PageFactory pageFactory;
    @Auto
    public OptimizedStudySetServiceImpl(StudySetRepository repository,
                                        StudySetMapper studySetMapper,
                                        ServiceProviderContainer services,
                                        UserStudySetService userStudySetService,
                                        OptimizedTermService optimizedTermService, PageFactory pageFactory) {
        this.repository = repository;
        this.studySetMapper = studySetMapper;
        this.services = services;
        this.userStudySetService = userStudySetService;
        this.optimizedTermService = optimizedTermService;
        this.pageFactory = pageFactory;
    }

    @Override
    public StudySetWithTermsResponseDTO create(StudySetWithCreatorDTO createDTO) {
        Long creatorId = createDTO.getCreatorId();
        StudySet saved = services.crud(repository).save(createDTO, studySetMapper::fromCreator).getRaw();
        userStudySetService.save(creatorId, saved.getId(), UserStudySetRelation.OWNER);
        return studySetMapper.toResponseWithTerms(saved);
    }

    @Override
    public StudySetDescriptionDTO getShortDescription(Long id) {
        Optional<SizedStudySet> byIdWithSize = (repository.findByIdWithSize(id));
        if (byIdWithSize.isEmpty()) {
            throw new EntityNotFoundException("Study set with id " + id + " not found");
        }
        SizedStudySet sizedStudySet = byIdWithSize.get();
        StudySet studySet = sizedStudySet.studySet();
        int termCount = sizedStudySet.size();
        return studySetMapper.toShortDescription(studySet, termCount);
    }

    private UserResponseDTO excludeOwner(List<UserWithRelation> relatedUsers, Long setId) {
        List<UserWithRelation> owners = relatedUsers.stream()
                .filter(user -> user.getRelationType().equals(UserStudySetRelation.OWNER))
                .toList();
        if (owners.isEmpty()) {
            throw new MissingEntityException("Study Set " + setId + " has no owner.");
        }
        if (owners.size() > 1) {
            throw new EntityStateException("Study Set " + setId + " has " + owners.size() + " owners.");
        }
        UserWithRelation result = owners.get(0);
        relatedUsers.remove(result);
        return result.getResponseDTO();
    }


    @Override
    public List<StudySetDescriptionDTO> getStudySetsByUser(Long userId) {
        List<StudySet> studySets = repository.findAllByUserId(userId);
        return setsToDescription(studySets);
    }

    @Override
    public List<StudySetDescriptionDTO> getStudySetsByUser(Long userId, Integer page, Integer size) {
        PageDescription pageable = pageFactory.pageDescription(page, size);
        Page<StudySet> allByUserIdAndPage = repository.findAllByUserId(userId, pageable);
        List<StudySet> studySets = allByUserIdAndPage.getElements();
        return setsToDescription(studySets);
    }

    private List<StudySetDescriptionDTO> setsToDescription(List<StudySet> studySets) {
        List<Integer> sizes = studySets.stream().map(s -> repository.getTermCount(s.getId())).toList();
        List<StudySetDescriptionDTO> result = new ArrayList<>();
        for (int i = 0; i < sizes.size(); i++) {
            result.add(studySetMapper.toShortDescription(studySets.get(i), sizes.get(i)));
        }
        return result;
    }

    @Override
    public StudySetDescriptionWithProgressDTO getDescription(Long id, Long userId) {
        Optional<SizedStudySet> byIdWithSize = (repository.findByIdWithSize(id));
        if (byIdWithSize.isEmpty()) {
            throw new EntityNotFoundException("Study set with id " + id + " not found");
        }
        SizedStudySet sizedStudySet = byIdWithSize.get();
        int studiedTerms = optimizedTermService.getUserProgress(id, userId);
        return studySetMapper.toProgressDescription(sizedStudySet.studySet(), sizedStudySet.size(), studiedTerms);
    }

    @Override
    public StudySetHeaderResponseDTO getHeader(Long id) {
        StudySet studySet = services.crud(repository)
                .findById(id)
                .orThrow("Study set with id " + id + " not found");
        List<UserWithRelation> users = userStudySetService.getUsersByStudySet(id);
        UserResponseDTO owner = excludeOwner(users, id);
        List<UserResponseDTO> savers = users.stream().map(UserWithRelation::getResponseDTO).toList();
        return studySetMapper.toHeader(studySet, owner, savers);
    }

}
