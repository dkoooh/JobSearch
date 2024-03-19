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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseDao responseDao;
    private final UserDao userDao;
    private final VacancyDao vacancyDao;

    public ResponseDto getResponseByVacancy (Integer vacancyId, String email) throws CustomException {
        if (vacancyId == null || !vacancyDao.getVacancies().stream().map(Vacancy::getId).toList().contains(vacancyId)) {
            throw new CustomException("Invalid vacancy ID");
        } else if (email == null || email.isBlank() || !userDao.isUserExists(email)) {
            throw new CustomException("Invalid email");
        }

        if (!"applicant".equalsIgnoreCase(userDao.getUserByEmail(email).get().getAccountType()) ||
                !vacancyDao.getVacanciesByUser(userDao.getUserByEmail(email).get().getId()).stream()
                        .map(Vacancy::getId)
                        .toList().contains(vacancyId)) {
            throw new CustomException("Access denied");
        }

        RespondedApplicant response = responseDao.getResponseByVacancy(vacancyId).orElseThrow(() ->
                new CustomException("Cannot find vacancy with ID: " + vacancyId));

        return ResponseDto.builder()
                .id(response.getId())
                .resumeId(response.getResumeId())
                .vacancyId(response.getVacancyId())
                .isConfirmed(response.getIsConfirmed())
                .build();
    }
}
