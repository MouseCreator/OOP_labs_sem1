package mouse.project.app.service;

import jakarta.persistence.EntityNotFoundException;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.app.defines.UserStudySetRelation;
import mouse.project.app.dto.user.UserResponseDTO;
import mouse.project.app.dto.user.UserWithRelation;
import mouse.project.app.dto.userstudyset.UserStudySetCreateDTO;
import mouse.project.app.dto.userstudyset.UserStudySetResponseDTO;
import mouse.project.app.dto.userstudyset.UserStudySetUpdateDTO;
import mouse.project.app.exception.EntityStateException;
import mouse.project.app.exception.MissingEntityException;
import mouse.project.lib.service.container.ServiceProviderContainer;
import mouse.project.app.mapper.UserMapper;
import mouse.project.app.mapper.UserStudySetMapper;
import mouse.project.app.model.StudySet;
import mouse.project.app.model.User;
import mouse.project.app.model.UserStudySet;
import mouse.project.app.dao.StudySetRepository;
import mouse.project.app.dao.UserRepository;
import mouse.project.app.dao.UserStudySetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserStudySetServiceImpl implements UserStudySetService {
    private final UserStudySetRepository repository;
    private final StudySetRepository studySetRepository;
    private final UserRepository userRepository;
    private final ServiceProviderContainer services;
    private final UserStudySetMapper mapper;
    private final UserMapper userMapper;
    private final UserTermService userTermService;
    public UserStudySetServiceImpl(UserStudySetRepository repository,
                                   StudySetRepository studySetRepository,
                                   UserRepository userRepository,
                                   ServiceProviderContainer services,
                                   UserStudySetMapper mapper,
                                   UserMapper userMapper,
                                   UserTermService userTermService) {
        this.repository = repository;
        this.studySetRepository = studySetRepository;
        this.userRepository = userRepository;
        this.services = services;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.userTermService = userTermService;
    }

    @Override
    public UserStudySetResponseDTO save(UserStudySetCreateDTO userStudySetCreateDTO) {
        Long userId = userStudySetCreateDTO.getUserId();
        Long studySetId = userStudySetCreateDTO.getStudySetId();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() ->
                new EntityNotFoundException("User with id " + userId + " not found."));
        Optional<StudySet> setOptional = studySetRepository.findById(studySetId);
        StudySet set = setOptional.orElseThrow(() ->
                new EntityNotFoundException("Set with id " + studySetId + " not found."));
        UserStudySet model = new UserStudySet(user, set, userStudySetCreateDTO.getType());
        UserStudySetResponseDTO result = services.use(repository).
                single(r -> r.save(model))
                .to(mapper::toResponse);

        userTermService.initializeProgress(userId, studySetId);
        return result;
    }

    @Override
    public UserStudySetResponseDTO update(Long userId, Long studySetId, String type) {
        UserStudySetUpdateDTO updateDTO = new UserStudySetUpdateDTO();
        updateDTO.setUserId(userId);
        updateDTO.setStudySetId(studySetId);
        updateDTO.setType(type);
        return update(updateDTO);
    }

    @Override
    public UserStudySetResponseDTO update(UserStudySetUpdateDTO userStudySetUpdateDTO) {
        return services.use(repository).
                single(r -> r.save(mapper.fromUpdate(userStudySetUpdateDTO)))
                .to(mapper::toResponse);
    }

    @Override
    public UserStudySetResponseDTO getByUserAndStudySet(Long userId, Long studySetId) {
        return services.use(repository)
                .optional(r -> r.findByUserAndStudySet(userId, studySetId))
                .orThrow(mapper::toResponse);
    }

    @Override
    public void removeByUserAndStudySet(Long userId, Long studySetId) {
        services.use(repository).none(r -> r.deleteByUserAndStudySet(userId, studySetId));
        userTermService.removeProgress(userId, studySetId);
    }

    @Override
    public List<UserStudySetResponseDTO> getAll() {
        return services.use(repository).multi(UserStudySetRepository::findAll).to(mapper::toResponse);
    }

    @Override
    public UserStudySetResponseDTO save(Long userId, Long studySetId, String type) {
        UserStudySetCreateDTO createDTO = new UserStudySetCreateDTO();
        createDTO.setUserId(userId);
        createDTO.setStudySetId(studySetId);
        createDTO.setType(type);
        return save(createDTO);
    }

    @Override
    public UserResponseDTO getOwnerOfStudySet(Long id) {
        List<UserStudySet> byUserAndType = repository.findByUserAndType(id, UserStudySetRelation.OWNER);
        if (byUserAndType.isEmpty()) {
            throw new MissingEntityException("Study set " + id + " has no owner");
        }
        if (byUserAndType.size() > 1) {
            throw new EntityStateException("Study set " + id + " has " + byUserAndType.size() + " owners.");
        }
        User user = byUserAndType.get(0).getUser();
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserWithRelation> getUsersByStudySet(Long userId) {
        List<UserStudySet> relatedUsers = repository.findByUser(userId);
        return relatedUsers.stream().map(u -> {
            UserResponseDTO response = userMapper.toResponse(u.getUser());
            return new UserWithRelation(response, u.getType());
        }).toList();
    }
}
