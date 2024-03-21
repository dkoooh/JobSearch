package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByName(String name);

    List<UserDto> getUsersByPhoneNumber(String phoneNumber);

    List<UserDto> getApplicantsByVacancy (Integer vacancyId, String email) throws CustomException;

    UserDto getEmployer (String employerEmail, String applicantEmail) throws CustomException;

    UserDto getApplicant (String employerEmail, String applicantEmail) throws CustomException;

    Boolean isUserExists (String email);

    void createUser (UserDto userDto) throws CustomException;

    void updateUser (UserDto userDto) throws CustomException;

    ResponseEntity<?> downloadUserAvatar(String userEmail) throws CustomException;

    void uploadUserAvatar(String userEmail, MultipartFile userImage) throws CustomException;
}
