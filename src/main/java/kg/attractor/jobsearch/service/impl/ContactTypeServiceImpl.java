package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.ContactType;
import kg.attractor.jobsearch.repository.ContactTypeRepository;
import kg.attractor.jobsearch.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements ContactTypeService {
    private final ContactTypeRepository contactTypeRepository;

    @Override
    public String getById(Integer typeId) {
        ContactType contactType = contactTypeRepository.findById(typeId).orElseThrow(() -> new NotFoundException(
                "Cannot find contact type with ID: " + typeId
        ));

        return contactType.getType();
    }

    @Override
    public Boolean isExists (Integer typeId) {
        return contactTypeRepository.existsById(typeId);
    }
}
