package kg.attractor.jobsearch.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;
import kg.attractor.jobsearch.model.ContactInfo;
import kg.attractor.jobsearch.repository.ContactInfoRepository;
import kg.attractor.jobsearch.repository.ContactTypeRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.ContactInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public void create(ContactInfoCreateDto dto, int resumeId) {
        ContactInfo info = ContactInfo.builder()
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.resume")))
                .type(contactTypeRepository.findById(dto.getTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.contactType")))
                .contactValue(dto.getContactValue())
                .build();

        contactInfoRepository.save(info);
    }

    public List<ContactInfoDto> getAllByResumeId(int resumeId) {
        List<ContactInfo> list = contactInfoRepository.findAllByResumeId(resumeId);

        return list.stream()
                .map(contactInfo -> ContactInfoDto.builder()
                        .type(contactInfo.getType().getType())
                        .value(contactInfo.getContactValue())
                        .build())
                .toList();
    }

    @Override
    public void update(ContactInfoUpdateDto dto, int resumeId) {
        ContactInfo info = ContactInfo.builder()
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.resume")))
                .type(contactTypeRepository.findById(dto.getTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.contactType")))
                .contactValue(dto.getContactValue())
                .build();

        contactInfoRepository.findByResumeIdAndTypeId(resumeId, info.getType().getId())
                .ifPresent(contactInfo ->
                        info.setId(contactInfo.getId())
                );

        contactInfoRepository.save(info);
    }

    @Override
    public ContactInfoUpdateDto convertToUpdateDto (ContactInfoDto dto) {
        return ContactInfoUpdateDto.builder()
                .typeId(contactTypeRepository.findByType(dto.getType()).orElseThrow().getId()) // TODO костыль
                .contactValue(dto.getValue())
                .build();
    }
}
