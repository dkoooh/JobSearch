package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.model.Authority;
import kg.attractor.jobsearch.repository.AuthorityRepository;
import kg.attractor.jobsearch.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository repository;

    @Override
    public Authority getById(Integer id) {
        return repository.getReferenceById(id);
    }

    @Override
    public Authority getByName(String name) {
        return repository.getReferenceByAuthorityName(name);
    }
}
