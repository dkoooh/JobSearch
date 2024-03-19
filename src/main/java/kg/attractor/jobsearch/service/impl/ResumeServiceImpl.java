package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @SneakyThrows
    public Resume getResumeById (int resumeId) {
        return resumeDao.getResumeById(resumeId).orElseThrow(() -> new CustomException("Cannot find resume with ID: " + resumeId));
    }

    public List<Resume> getResumesByCategory (int categoryId) {
        return resumeDao.getResumesByCategory(categoryId);
    }

    public List<Resume> getResumesByApplicant (int applicantId) {
        return resumeDao.getResumesByApplicant(applicantId);
    }

    public void createResume (Resume resume) {
        resumeDao.createResume(resume);
    }

    public void updateResume (Resume resume) {
        resumeDao.updateResume(resume);
    }

    public void deleteResume (int resumeId) {
        resumeDao.deleteResume(resumeId);
    }
}
