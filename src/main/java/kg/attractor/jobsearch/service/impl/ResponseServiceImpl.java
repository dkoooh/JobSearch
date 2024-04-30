package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResponseDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.ResponseDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.exception.NoAccessException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseDao responseDao;
    private final UserDao userDao;
    private final UserService userService;
    private final VacancyDao vacancyDao;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

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
                .resume(resumeService.getResumeById(respondedApplicant.getResumeId()))
                .vacancy(vacancyService.getVacancyById(respondedApplicant.getVacancyId()))
                .isConfirmed(respondedApplicant.getIsConfirmed())
                .build();
    }

    @Override
    public ResponseDto getById(Integer id, String userEmail) {

        RespondedApplicant response = responseDao.getById(id).orElseThrow(
                () -> new NotFoundException("Cannot find response with ID: " + id)
        );

        System.out.println("ResponseService authorized: " + SecurityContextHolder.getContext().getAuthentication());

        ResponseDto responseDto = ResponseDto.builder()
                .id(response.getId())
                .vacancy(vacancyService.getVacancyById(response.getVacancyId()))
                .resume(resumeService.getResumeById(response.getResumeId()))
                .isConfirmed(response.getIsConfirmed())
                .build();

        if (!userService.getUserByEmail(userEmail).getId().equals(responseDto.getVacancy().getAuthor().getId()) &&
                !userService.getUserByEmail(userEmail).getId().equals(responseDto.getResume().getApplicant().getId())) {
            throw new NoAccessException("Cannot get info of the response you're not a member of");
        }

        return responseDto;
    }

    @Override
    public ResponseDto getById(Integer id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return getById(id, userEmail);
    }

    public List<Map<String, Object>> fetchAllGroups () {
        UserDto user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Map<String, Object>> responses = responseDao.fetchAllGroups(user.getId());

        responses.forEach(stringObjectMap -> {
            VacancyDto vacancy = vacancyService.getVacancyById((Integer)stringObjectMap.get("VACANCY_ID"));
            stringObjectMap.remove("VACANCY_ID");
            stringObjectMap.put("VACANCY", vacancy);

            ResumeDto resume = resumeService.getResumeById((Integer)stringObjectMap.get("RESUME_ID"));
            stringObjectMap.remove("RESUME_ID");
            stringObjectMap.put("RESUME", resume);
        });

        return responses;
    }

    @Override
    public void create(Integer vacancyId, Integer resumeId) {
        responseDao.create(vacancyId, resumeId);
    }
}
