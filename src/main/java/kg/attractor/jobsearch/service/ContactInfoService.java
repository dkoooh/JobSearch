package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoDto;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;

import java.util.List;

public interface ContactInfoService {
    void create(ContactInfoCreateDto contactInfoCreateDto, int resumeId);

    List<ContactInfoDto> getByResumeId (int resumeId);

    void update(ContactInfoUpdateDto contactInfoUpdateDto, int resumeId);
}
