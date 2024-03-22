package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ContactInfoDao;
import kg.attractor.jobsearch.dao.ContactTypeDao;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.ContactInfo;
import kg.attractor.jobsearch.service.ContactInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoDao contactInfoDao;
    private final ContactTypeDao contactTypeDao;

    @Override
    public void create(ContactInfoCreateDto contactInfoCreateDto, int resumeId) {
        if (!contactTypeDao.isTypeExists(contactInfoCreateDto.getTypeId())) {
            throw new CustomException("Invalid type ID");
        }

        ContactInfo info = ContactInfo.builder()
                .resumeId(resumeId)
                .typeId(contactInfoCreateDto.getTypeId())
                .contactValue(contactInfoCreateDto.getContactValue())
                .build();

        contactInfoDao.create(info);
    }

    public List<ContactInfoDto> getByResumeId (int resumeId) {
        List<ContactInfo> list = contactInfoDao.getByResumeId(resumeId);

        return list.stream()
                .map(contactInfo -> ContactInfoDto.builder()
                        .typeId(contactInfo.getTypeId())
                        .value(contactInfo.getContactValue())
                        .build())
                .toList();
    }

    @Override
    public void update(ContactInfoUpdateDto contactInfoUpdateDto, int resumeId) {
        ContactInfo info = ContactInfo.builder()
                .resumeId(resumeId)
                .typeId(contactInfoUpdateDto.getTypeId())
                .contactValue(contactInfoUpdateDto.getContactValue())
                .build();

        if (contactInfoDao.isContactTypeExists(contactInfoUpdateDto.getTypeId(), resumeId)) {
            contactInfoDao.update(info);
        } else {
            contactInfoDao.create(info);
        }
    }
}
