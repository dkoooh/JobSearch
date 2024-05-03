package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.config.SecurityConfig;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserLoginDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.AuthorityService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final FileUtil fileUtil;
    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private final AuthorityService authorityService;

    private final PasswordEncoder encoder = SecurityConfig.encoder();

    @Override
    public void create(UserCreationDto userDto) {
        User newUser = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .password(encoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .avatar("_default_avatar.png")
                .accountType(userDto.getAccountType())
                .authorities(new HashSet<>(
                        Set.of(authorityService.getByName(userDto.getAccountType())))
                )
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

        userRepository.save(user);
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
            throw new NotFoundException("Invalid vacancy");
        }
        List<User> applicants = userDao.getApplicantsByVacancy(vacancyId);

        List<UserDto> dtos = new ArrayList<>();
        applicants.forEach(
                user -> dtos.add(transformToDto(user))
        );

        return dtos;
    }

    @Override
    public UserDto getEmployer(Integer employerId) {
        return getById(employerId);
    }

    @Override
    public UserDto getApplicant(Integer applicantId) {
        return getById(applicantId);
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
    public void login(Authentication auth, UserLoginDto userDto) {
        Optional<User> foundUser = userRepository.findByEmail(userDto.getUsername());

        if (foundUser.isEmpty()) {
            throw new NotFoundException("Bad Credentials");
        }

        if (!new BCryptPasswordEncoder().matches(userDto.getPassword(), foundUser.get().getPassword())) {
            throw new NotFoundException("Bad Credentials");
        }
    }

    @Override
    public ResponseEntity<?> downloadUserAvatar(String userEmail) {
        String fileName = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found. The requested user does not exist"))
                .getAvatar();
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
}
