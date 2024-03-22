package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResponseDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.ResponseDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseDao responseDao;
    private final UserDao userDao;
    private final VacancyDao vacancyDao;

    public ResponseDto getResponseByVacancy(Integer vacancyId, String email){
        if (vacancyId == null || !vacancyDao.getVacancies().stream().map(Vacancy::getId).toList().contains(vacancyId)) {
            throw new CustomException("Invalid vacancy ID");
        } else if (email == null || email.isBlank() || !userDao.isUserExists(email)) {
            throw new CustomException("Invalid email");
        }

        if (!"applicant".equalsIgnoreCase(userDao.getUserByEmail(email).get().getAccountType())) {
            throw new CustomException("Access denied");
        }

        int applicantId = userDao.getUserByEmail(email).get().getId();

        RespondedApplicant respondedApplicant = responseDao.getResponseByVacancy(vacancyId, applicantId)
                .orElseThrow(() -> new CustomException("Not found")
        );

        return ResponseDto.builder()
                .id(respondedApplicant.getId())
                .resumeId(respondedApplicant.getResumeId())
                .vacancyId(respondedApplicant.getVacancyId())
                .isConfirmed(respondedApplicant.getIsConfirmed())
                .build();
    }
}
