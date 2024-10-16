package kg.attractor.jobsearch.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserLoginDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    UserDto getByEmail(String email);

    UserDto getById(int userId);

    List<UserDto> getAllByName(String name);

    List<UserDto> getAllByPhoneNumber(String phoneNumber);

    List<UserDto> getApplicantsByVacancy (Integer vacancyId, String email);

    UserDto getEmployer (String employerEmail);

    UserDto getApplicant (String applicantEmail);

    Boolean exists(String email);

    void create(UserCreationDto userDto);

    void update(Authentication auth, UserUpdateDto dto, Integer userId);

    ResponseEntity<?> downloadUserAvatar(String userEmail);

    void uploadAvatar(String userEmail, MultipartFile userImage);

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String password);

    void makeResetPasswdLink(HttpServletRequest request) throws UsernameNotFoundException, UnsupportedEncodingException, MessagingException;
}
