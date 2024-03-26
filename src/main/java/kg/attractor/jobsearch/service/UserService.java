package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByName(String name);

    List<UserDto> getUsersByPhoneNumber(String phoneNumber);

    List<UserDto> getApplicantsByVacancy (Integer vacancyId, String email);

    UserDto getEmployer (String employerEmail, String applicantEmail);

    UserDto getApplicant (String employerEmail, String applicantEmail);

    Boolean isUserExists (String email);

    void create(UserCreationDto userDto);

    void update(Authentication auth, UserUpdateDto dto, Integer userId);

    ResponseEntity<?> downloadUserAvatar(String userEmail);

    void uploadUserAvatar(String userEmail, MultipartFile userImage);
}
