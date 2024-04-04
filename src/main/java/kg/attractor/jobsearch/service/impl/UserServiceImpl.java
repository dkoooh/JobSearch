package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.FileUtil;
import kg.attractor.jobsearch.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final VacancyDao vacancyDao;
    private final FileUtil fileUtil;

    public void create (UserCreationDto userDto){
        if (isUserExists(userDto.getEmail())) {
            throw new CustomException("User with this email is already exists");
        } else if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new CustomException("Email is blank");
        } else if (userDto.getAge() != null && userDto.getAge() < 16) {
            throw new CustomException("User is too young");
        } else if (userDto.getAccountType() == null || !("employer".equalsIgnoreCase(userDto.getAccountType()) ||
                "applicant".equalsIgnoreCase(userDto.getAccountType()))) {
            throw new CustomException("Invalid account type");
        } else if (userDto.getName() == null || userDto.getName().isBlank()) {
            throw new CustomException("Name is empty");
        } else if (userDto.getPassword() == null) {
            throw new CustomException("Password is empty");
        } else if (userDto.getPhoneNumber() == null) {
            throw new CustomException("Phone number is empty");
        }

        User newUser = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
//                .avatar(avatarFileName)
                .accountType(userDto.getAccountType())
                .build();

        userDao.createUser(newUser);
    }

    public void update(Authentication auth, UserUpdateDto dto, Integer userId) {

        if (!userId.equals(userDao.getUserByEmail(auth.getName()).get().getId())) {
            throw new CustomException("Access denied");
        }

        User user = User.builder()
                .id(userId)
                .name(dto.getName())
                .surname(dto.getSurname())
                .age(dto.getAge())
                .password(new BCryptPasswordEncoder().encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .build();

        userDao.updateUser(user);
    }

    @SneakyThrows
    public UserDto getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email).orElseThrow(() -> new CustomException("Cannot find user with email: "
                + email));

        return transformToDto(user);
    }

    public List<UserDto> getUsersByName(String name) {
        List<User> users = userDao.getUsersByName(name);

        List<UserDto> dtos = new ArrayList<>();
        users.forEach(
                user -> dtos.add(transformToDto(user))
        );

        return dtos;
    }

    public List<UserDto> getUsersByPhoneNumber(String phoneNumber) {
        List<User> users = userDao.getUsersByPhoneNumber(phoneNumber);

        List<UserDto> dtos = new ArrayList<>();
        users.forEach(
                user -> dtos.add(transformToDto(user))
        );

        return dtos;
    }

    public List<UserDto> getApplicantsByVacancy(Integer vacancyId, String email) {
        Utils.verifyUser(email, "employer", userDao);

        if (vacancyId == null || !vacancyDao.getVacancies().stream().map(Vacancy::getId).toList().contains(vacancyId)) {
            throw new CustomException("Invalid vacancy");
        }
        List<User> applicants = userDao.getApplicantsByVacancy(vacancyId);

        List<UserDto> dtos = new ArrayList<>();
        applicants.forEach(
                user -> dtos.add(transformToDto(user))
        );

        return dtos;
    }

    public UserDto getEmployer(String employerEmail, String applicantEmail) {
        validateApplicantAndEmployerEmail(employerEmail, applicantEmail);
        return getUserByEmail(employerEmail);
    }

    public UserDto getApplicant(String employerEmail, String applicantEmail) {
        validateApplicantAndEmployerEmail(employerEmail, applicantEmail);
        return getUserByEmail(applicantEmail);
    }

    public Boolean isUserExists(String email) {
        return userDao.isUserExists(email);
    }

    @Override
    public void uploadUserAvatar(String userEmail, MultipartFile userImage) {
        Utils.verifyUser(userEmail, userDao);

        String fileName = fileUtil.saveUploadedFile(userImage, "images/users/");
        userDao.uploadUserAvatar(userEmail, fileName);
    }

    @Override
    public ResponseEntity<?> downloadUserAvatar(String userEmail) {
        String fileName = userDao.getUserByEmail(userEmail).get().getAvatar();
        return fileUtil.getOutputFile(fileName, "images/users/", MediaType.IMAGE_PNG);
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

    private void validateApplicantAndEmployerEmail(String employerEmail, String applicantEmail) {
        if (applicantEmail == null || !isUserExists(applicantEmail) ||
                !"applicant".equalsIgnoreCase(userDao.getUserByEmail(applicantEmail).get().getAccountType())) {
            throw new CustomException("Access denied");
        }

        Utils.verifyUser(applicantEmail, "applicant", userDao);

        Utils.verifyUser(employerEmail, "employer", userDao);
    }
}
