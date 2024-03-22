package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ContactInfoDao;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;
import kg.attractor.jobsearch.model.ContactInfo;
import kg.attractor.jobsearch.service.ContactInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoDao contactInfoDao;

    @Override
    public void create(ContactInfoCreateDto contactInfoCreateDto, int resumeId) {
        ContactInfo info = ContactInfo.builder()
                .resumeId(resumeId)
                .typeId(contactInfoCreateDto.getTypeId())
                .value(contactInfoCreateDto.getContactValue())
                .build();

        contactInfoDao.create(info);
    }

    @Override
    public void update(ContactInfoUpdateDto contactInfoUpdateDto, int resumeId) {
        ContactInfo info = ContactInfo.builder()
                .resumeId(resumeId)
                .typeId(contactInfoUpdateDto.getTypeId())
                .value(contactInfoUpdateDto.getContactValue())
                .build();

        if (contactInfoDao.isContactTypeExists(contactInfoUpdateDto.getTypeId(), resumeId)) {
            contactInfoDao.update(info);
        } else {
            contactInfoDao.create(info);
        }
    }
}
