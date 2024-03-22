package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;

public interface ContactInfoService {
    void create(ContactInfoCreateDto contactInfoCreateDto, int resumeId);

    void update(ContactInfoUpdateDto contactInfoUpdateDto, int resumeId);
}
