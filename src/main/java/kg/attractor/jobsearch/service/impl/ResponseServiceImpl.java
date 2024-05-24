package kg.attractor.jobsearch.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.jobsearch.dao.ResponseDao;
import kg.attractor.jobsearch.dto.response.ResponseDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.exception.ForbiddenException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.repository.ResponseRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.repository.VacancyRepository;
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
    private final UserService userService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final ResponseRepository responseRepository;
    private final ResumeRepository resumeRepository;
    private final VacancyRepository vacancyRepository;

//    public ResponseDto getResponseByVacancy(Integer vacancyId, String email){
//        if (!vacancyService.isExists(vacancyId)) {
//            throw new CustomException("Invalid vacancy ID");
//        }
//
////        if (!"applicant".equalsIgnoreCase(userDao.getUserByEmail(email).get().getAccountType())) {
////            throw new CustomException("Access denied");
////        }
//
//        int applicantId = userDao.getUserByEmail(email).get().getId();
//
//        RespondedApplicant respondedApplicant = responseRepository.findByVacancyIdAndResumeId(vacancyId, applicantId)
//                .orElseThrow(() -> new CustomException("Not found")
//        );
//
//        return ResponseDto.builder()
//                .id(respondedApplicant.getId())
//                .resume(resumeService.getById(respondedApplicant.getResume().getId()))
//                .vacancy(vacancyService.getVacancyById(respondedApplicant.getVacancy().getId()))
//                .isConfirmed(respondedApplicant.getIsConfirmed())
//                .build();
//    }

    @Override
    public ResponseDto getById(Integer id, String userEmail) {

        RespondedApplicant response = responseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("error.notFound.response")
        );

        System.out.println("ResponseService authorized: " + SecurityContextHolder.getContext().getAuthentication());

        ResponseDto responseDto = ResponseDto.builder()
                .id(response.getId())
                .vacancy(vacancyService.getById(response.getVacancy().getId()))
                .resume(resumeService.getById(response.getResume().getId()))
                .isConfirmed(response.getIsConfirmed())
                .build();

        Integer employerId = responseDto.getVacancy().getAuthor().getId();
        Integer applicantId = responseDto.getResume().getApplicant().getId();
        Integer userId = userService.getByEmail(userEmail).getId();

        if ( !(userId.equals(employerId) || userId.equals(applicantId)) ) {
            throw new ForbiddenException("error.forbidden.response");
        }

        return responseDto;
    }

    @Override
    public ResponseDto getById(Integer id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return getById(id, userEmail);
    }

    @Override
    public List<Map<String, Object>> fetchAllGroups() {
        UserDto user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Map<String, Object>> responses = responseDao.fetchAllGroups(user.getId());

        responses.forEach(stringObjectMap -> {
            VacancyDto vacancy = vacancyService.getById((Integer) stringObjectMap.get("VACANCY_ID"));
            stringObjectMap.remove("VACANCY_ID");
            stringObjectMap.put("VACANCY", vacancy);

            ResumeDto resume = resumeService.getById((Integer) stringObjectMap.get("RESUME_ID"));
            stringObjectMap.remove("RESUME_ID");
            stringObjectMap.put("RESUME", resume);
        });

        return responses;
    }

    @Override
    @Transactional
    public void create(Integer vacancyId, Integer resumeId) {
        RespondedApplicant response = RespondedApplicant.builder()
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.resume")))
                .vacancy(vacancyRepository.findById(vacancyId)
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.vacancy")))
                .isConfirmed(false)
                .build();
        responseRepository.saveAndFlush(response);
    }
}
