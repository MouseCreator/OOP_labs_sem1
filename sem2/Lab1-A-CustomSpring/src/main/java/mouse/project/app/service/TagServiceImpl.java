package mouse.project.app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mouse.project.lib.ioc.annotation.Service;
import mouse.project.app.dto.tag.TagCreateDTO;
import mouse.project.app.dto.tag.TagResponseDTO;
import mouse.project.app.dto.tag.TagUpdateDTO;
import mouse.project.lib.service.container.ServiceProviderContainer;
import mouse.project.app.mapper.TagMapper;
import mouse.project.app.model.User;
import mouse.project.app.dao.TagRepository;
import mouse.project.app.dao.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final TagMapper tagMapper;
    private final ServiceProviderContainer services;
    private final UserRepository userRepository;
    @Override
    public List<TagResponseDTO> getAll() {
        return services.crud(repository).
                findAll().
                to(tagMapper::toResponse);
    }

    @Override
    public TagResponseDTO getById(Long id) {
        return services.crud(repository)
                .findById(id).orThrow(tagMapper::toResponse);
    }

    @Override
    public TagResponseDTO save(TagCreateDTO createDTO) {
        Optional<User> user = userRepository.findById(createDTO.getOwnerId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("No owner for creating tag: " + createDTO);
        }
        return services.
                crud(repository).
                save(createDTO, tagMapper::fromCreate).
                to(tagMapper::toResponse);
    }

    @Override
    public TagResponseDTO update(TagUpdateDTO updateDTO) {
        Optional<User> user = userRepository.findById(updateDTO.getOwnerId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("No owner for updating tag: " + updateDTO);
        }
        return services.
                crud(repository).
                update(updateDTO, tagMapper::fromUpdate).
                to(tagMapper::toResponse);
    }

    @Override
    public void removeById(Long id) {
        services.crud(repository).removeById(id);
    }

    @Override
    public TagResponseDTO hardGet(Long id) {
        return services.soft(repository)
                .getByIdIncludeDeleted(id).orThrow(tagMapper::toResponse);
    }

    @Override
    public List<TagResponseDTO> getAllWithDeleted() {
        return services.soft(repository)
                .findAllWithDeleted()
                .to(tagMapper::toResponse);
    }

    @Override
    public List<TagResponseDTO> getAllByUser(Long userId) {
        return services.use(repository).
                multi(r -> r.getTagsByOwner(userId)).
                to(tagMapper::toResponse);
    }

    @Override
    public List<TagResponseDTO> getAllByUserAndName(Long userId, String name) {
        return services.use(repository)
                .multi(r -> r.getTagsByOwnerAndName(userId, name))
                .to(tagMapper::toResponse);
    }

    @Override
    public void restoreById(Long id) {
        services.soft(repository).restoreById(id);
    }
}
