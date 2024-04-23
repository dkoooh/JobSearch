package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResponseDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.ResponseDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.exception.NoAccessException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseDao responseDao;
    private final UserDao userDao;
    private final UserService userService;
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

    @Override
    public ResponseDto getById(Integer id) {

        RespondedApplicant response = responseDao.getById(id).orElseThrow(
                () -> new NotFoundException("Cannot find response with ID: " + id)
        );

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();



        ResponseDto responseDto = ResponseDto.builder()
                .id(response.getId())
                .vacancyId(response.getVacancyId())
                .resumeId(response.getResumeId())
                .isConfirmed(response.getIsConfirmed())
                .build();

        if (!userService.getUserByEmail(userEmail).getId().equals(responseDto.getVacancyId()) &&
                !userService.getUserByEmail(userEmail).getId().equals(responseDto.getResumeId())) {
            throw new NoAccessException("Cannot get info of the response you're not a member of");
        }

        return responseDto;
    }

//   public List<Map<String, Object>> fetchAll (String myId) {
//       List<Map<String, Object>> getAllUsers = template.queryForList("select * from users where id!=?", myId);
//
//       return getAllUsers;
//   }

    public List<Map<String, Object>> fetchAllGroups () {
        UserDto user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return responseDao.fetchAllGroups(user.getId());
    }
}
