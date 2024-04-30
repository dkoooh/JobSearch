package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ContactTypeDao;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.ContactType;
import kg.attractor.jobsearch.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements ContactTypeService {
    private final ContactTypeDao contactTypeDao;

    @Override
    public String getById(Integer typeId) {
        ContactType contactType = contactTypeDao.getById(typeId).orElseThrow(() -> new NotFoundException(
                "Cannot find contact type with ID: " + typeId
        ));

        return contactType.getType();
    }
}
