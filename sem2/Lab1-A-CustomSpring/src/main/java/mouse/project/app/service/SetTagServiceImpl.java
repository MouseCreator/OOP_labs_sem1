package mouse.project.app.service;

import jakarta.persistence.EntityNotFoundException;
import mouse.project.app.dao.SetTagRepository;
import mouse.project.app.dao.StudySetRepository;
import mouse.project.app.dao.TagRepository;
import mouse.project.app.dao.UserRepository;
import mouse.project.app.dto.settag.SetTagCreateDTO;
import mouse.project.app.dto.settag.SetTagResponseDTO;
import mouse.project.app.dto.studyset.StudySetResponseDTO;
import mouse.project.app.mapper.SetTagMapper;
import mouse.project.app.mapper.StudySetMapper;
import mouse.project.app.model.SetTag;
import mouse.project.app.model.StudySet;
import mouse.project.app.model.Tag;
import mouse.project.app.model.User;
import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.lib.service.container.ServiceProviderContainer;

import java.util.List;
import java.util.Optional;

@Service
public class SetTagServiceImpl implements SetTagService {

    private final SetTagRepository repository;
    private final SetTagMapper mapper;
    private final ServiceProviderContainer services;
    private final StudySetMapper studySetMapper;
    private final UserRepository userRepository;
    private final StudySetRepository studySetRepository;
    private final TagRepository tagRepository;
    @Auto
    public SetTagServiceImpl(SetTagRepository repository,
                             SetTagMapper mapper,
                             ServiceProviderContainer services,
                             StudySetMapper studySetMapper,
                             UserRepository userRepository,
                             StudySetRepository studySetRepository,
                             TagRepository tagRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.services = services;
        this.studySetMapper = studySetMapper;
        this.userRepository = userRepository;
        this.studySetRepository = studySetRepository;
        this.tagRepository = tagRepository;
    }

    private SetTagResponseDTO save(SetTag setTag) {
        return services.use(repository)
                .single(r -> r.save(setTag))
                .to(mapper::toResponse);
    }

    @Override
    public List<SetTagResponseDTO> getAll() {
        return services.use(repository)
                .multi(SetTagRepository::getAll).to(mapper::toResponse);
    }

    @Override
    public SetTagResponseDTO save(SetTagCreateDTO setTag) {
        return save(setTag.getUserId(), setTag.getStudySetId(), setTag.getTagId());
    }

    @Override
    public SetTagResponseDTO save(Long userId, Long setId, Long tagId) {
       return getAndSave(userId, setId, tagId);
    }

    private SetTagResponseDTO getAndSave(Long userId, Long setId, Long tagId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<StudySet> studySetOpt = studySetRepository.findById(setId);
        Optional<Tag> tagOpt = tagRepository.findById(tagId);
        User user = unpack(userOpt, "User", userId);
        StudySet studySet = unpack(studySetOpt, "Study Set", setId);
        Tag tag = unpack(tagOpt, "Tag", tagId);
        SetTag setTag = new SetTag(user, studySet, tag);

        return save(setTag);
    }

    private <T> T unpack(Optional<T> optional, String name, Long id) {
        return optional.orElseThrow(() -> new EntityNotFoundException(name + " is not found by id: " + id));
    }

    @Override
    public SetTagResponseDTO getSetTagById(Long userId, Long setId, Long tagId) {
        return services.use(repository)
                .optional(r -> r.getSetTagById(userId, setId, tagId))
                .orThrow(mapper::toResponse);
    }

    @Override
    public List<StudySetResponseDTO> getStudySetsByUserAndTags(Long userId, List<Long> tagIds) {
        return services.use(repository)
                .multi(r -> r.getStudySetsByUserAndTags(userId, tagIds))
                .to(studySetMapper::toResponse);
    }

    @Override
    public void delete(Long userId, Long setId, Long tagId) {
        services.use(repository).none(r -> r.delete(userId, setId, tagId));
    }
}
