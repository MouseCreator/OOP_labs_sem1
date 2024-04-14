package mouse.project.app.service;

import mouse.project.lib.ioc.annotation.Service;
import mouse.project.app.dto.term.TermCreateDTO;
import mouse.project.app.dto.term.TermResponseDTO;
import mouse.project.app.dto.term.TermUpdateDTO;
import mouse.project.lib.service.container.ServiceProviderContainer;
import mouse.project.app.mapper.TermMapper;
import mouse.project.app.dao.TermRepository;

import java.util.List;

@Service
public class TermServiceImpl implements TermService {

    private final ServiceProviderContainer services;
    private final TermRepository repository;
    private final TermMapper mapper;

    public TermServiceImpl(ServiceProviderContainer services,
                           TermRepository termRepository,
                           TermMapper mapper) {
        this.services = services;
        this.repository = termRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TermResponseDTO> getAll() {
        return services.crud(repository).findAll().to(mapper::toResponse);
    }

    @Override
    public TermResponseDTO getById(Long id) {
        return services.crud(repository).findById(id).orThrow(mapper::toResponse);
    }

    @Override
    public TermResponseDTO save(TermCreateDTO createDTO) {
        return services.crud(repository).save(createDTO, mapper::fromCreate).to(mapper::toResponse);
    }

    @Override
    public TermResponseDTO update(TermUpdateDTO updateDTO) {
        return services.crud(repository).update(updateDTO, mapper::fromUpdate).to(mapper::toResponse);
    }

    @Override
    public void removeById(Long id) {
        services.crud(repository).removeById(id);
        repository.removeTermFormStudySetsById(id);
    }

    @Override
    public List<TermResponseDTO> getAllWithDeleted() {
       return services.soft(repository).findAllWithDeleted().to(mapper::toResponse);
    }

    @Override
    public void restoreById(Long id) {
        services.soft(repository).restoreById(id);
    }
}
