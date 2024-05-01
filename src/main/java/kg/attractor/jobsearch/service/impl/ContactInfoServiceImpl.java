package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ContactInfoDao;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.ContactInfo;
import kg.attractor.jobsearch.repository.ContactInfoRepository;
import kg.attractor.jobsearch.repository.ContactTypeRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.ContactInfoService;
import kg.attractor.jobsearch.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;

    private final ContactInfoDao contactInfoDao;
    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeService contactTypeService;
    private final ResumeRepository resumeRepository;

    @Override
    public void create(ContactInfoCreateDto dto, int resumeId) {
        if (!contactTypeService.isExists(dto.getTypeId())) {
            throw new CustomException("Invalid type ID");
        }

        ContactInfo info = ContactInfo.builder()
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new NotFoundException("Cannot find resume by ID: " + resumeId)))
                .type(contactTypeRepository.findById(dto.getTypeId())
                        .orElseThrow(() -> new NotFoundException("Not found contact type with ID:" + dto.getTypeId())))
                .contactValue(dto.getContactValue())
                .build();

        contactInfoRepository.save(info);
    }

    public List<ContactInfoDto> getByResumeId (int resumeId) {
        List<ContactInfo> list = contactInfoDao.getByResumeId(resumeId);

        return list.stream()
                .map(contactInfo -> ContactInfoDto.builder()
                        .type(contactTypeService.getById(contactInfo.getId()))
                        .value(contactInfo.getContactValue())
                        .build())
                .toList();
    }

    @Override
    public void update(ContactInfoUpdateDto dto, int resumeId) {
        ContactInfo info = ContactInfo.builder()
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new NotFoundException("Not found resume with ID: " + resumeId)))
                .type(contactTypeRepository.findById(dto.getTypeId())
                        .orElseThrow(() -> new NotFoundException("Not found contact type with ID: " + dto.getTypeId())))
                .contactValue(dto.getContactValue())
                .build();

        contactInfoRepository.save(info);
    }
}
