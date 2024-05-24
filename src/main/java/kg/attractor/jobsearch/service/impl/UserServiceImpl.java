package kg.attractor.jobsearch.service.impl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.config.SecurityConfig;
import kg.attractor.jobsearch.dto.user.*;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.exception.UserAlreadyExistsException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.EmailService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.FileUtil;
import kg.attractor.jobsearch.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final FileUtil fileUtil;
    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private final EmailService emailService;

    private final PasswordEncoder encoder = SecurityConfig.encoder();

    @Override
    public void create(UserCreationDto userDto) {
        if (exists(userDto.getEmail())) {
            throw new UserAlreadyExistsException("User with this email is already exists");
        }

        User newUser = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .avatar("_default_avatar.png")
                .accountType(userDto.getAccountType())
                .enabled(true)
                .build();

        userRepository.save(newUser);
    }

    @Override
    public void update(Authentication auth, UserUpdateDto dto, Integer userId) {
        UserDto oldUser = getByEmail(auth.getName());

        User user = User.builder()
                .id(userId)
                .email(oldUser.getEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .age(dto.getAge())
                .password(encoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .enabled(true)
                .accountType(oldUser.getAccountType())
                .build();

        if (!dto.getAvatar().isEmpty()) {
            String filename = fileUtil.saveUploadedFile(dto.getAvatar(), "images/users");
            user.setAvatar(filename);
        } else {
            user.setAvatar(oldUser.getAvatar());
        }

        userRepository.saveAndFlush(user);
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found. The requested user does not exist"));

        return transformToDto(user);
    }

    @Override
    public UserDto getById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(("User not found. The requested user does not exist")));

        return transformToDto(user);
    }

    @Override
    public List<UserDto> getAllByName(String name) {
        List<User> users = userRepository.findAllByName(name);

        List<UserDto> dtos = new ArrayList<>();
        users.forEach(
                user -> dtos.add(transformToDto(user))
        );

        return dtos;
    }

    @Override
    public List<UserDto> getAllByPhoneNumber(String phoneNumber) {
        List<User> users = userRepository.findAllByPhoneNumber(phoneNumber);

        List<UserDto> dtos = new ArrayList<>();
        users.forEach(
                user -> dtos.add(transformToDto(user))
        );

        return dtos;
    }

    @Override
    public List<UserDto> getApplicantsByVacancy(Integer vacancyId, String email) {

        if (!vacancyRepository.existsById(vacancyId)) {
            throw new NotFoundException("Vacancy not found. The requested vacancy does not exist.");
        }
        List<User> applicants = userRepository.findApplicantsByVacancyId(vacancyId);

        List<UserDto> dtos = new ArrayList<>();
        applicants.forEach(
                user -> dtos.add(transformToDto(user))
        );

        return dtos;
    }

    @Override
    public UserDto getEmployer(String employerEmail) {
        UserDto employer = getByEmail(employerEmail);

        if (!"EMPLOYER".equals(employer.getAccountType())) {
            throw new NotFoundException("User not found. The requested user does not exist");
        }

        return employer;
    }

    @Override
    public UserDto getApplicant(String applicantEmail) {
        UserDto applicant = getByEmail(applicantEmail);

        if (!"APPLICANT".equals(applicant.getAccountType())) {
            throw new NotFoundException("User not found. The requested user does not exist");
        }

        return applicant;
    }

    @Override
    public Boolean exists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void uploadAvatar(String userEmail, MultipartFile userImage) {
        String fileName = fileUtil.saveUploadedFile(userImage, "images/users/");
        User user = User.builder()
                .email(userEmail)
                .avatar(fileName)
                .build();

        userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> downloadUserAvatar(String userEmail) {
        String fileName = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found. The requested user does not exist"))
                .getAvatar();
        return fileUtil.getOutputFile(fileName, "images/users/", MediaType.IMAGE_PNG);
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new NotFoundException("User not found. The requested user does not exist"));
    }

    @Override
    public void updatePassword (User user, String password) {
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.saveAndFlush(user);

    }

    @Override
    public void makeResetPasswdLink(HttpServletRequest request) throws UsernameNotFoundException, UnsupportedEncodingException, MessagingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);
        String resetPasswordLink = Utilities.getSiteURL(request) + "/account/reset_password?token=" + token;
        emailService.sendMail(email, resetPasswordLink);
    }

    private UserDto transformToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    private void updateResetPasswordToken (String token, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found. The requested user does not exist"));

        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }
}
