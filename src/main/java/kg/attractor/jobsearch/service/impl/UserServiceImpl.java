package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @SneakyThrows
    public UserDto getUserByEmail(String email){
        User user = userDao.getUserByEmail(email).orElseThrow(() -> new CustomException("Cannot find user with email: "
                + email));

        return transformToDto(user);
    }

    public List<UserDto> getUsersByName(String name){
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


    public List<UserDto> getApplicantsByVacancy (int vacancyId) {
        List<User> applicants = userDao.getApplicantsByVacancy(vacancyId);

        List<UserDto> dtos = new ArrayList<>();
        applicants.forEach(
                user -> dtos.add(transformToDto(user))
        );

        return dtos;
    }

    public UserDto getEmployer (String employerEmail, String applicantEmail) throws CustomException{
        validateApplicantAndEmployerEmail(employerEmail, applicantEmail);
        return getUserByEmail(employerEmail);
    }

    public UserDto getApplicant (String employerEmail, String applicantEmail) throws CustomException{
        validateApplicantAndEmployerEmail(employerEmail, applicantEmail);
        return getUserByEmail(applicantEmail);
    }


    public Boolean isUserExists (String email) {
        return userDao.isUserExists(email);
    }

    public void createUser (UserDto userDto) throws CustomException{
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
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .avatar(userDto.getAvatar())
                .accountType(userDto.getAccountType())
                .build();

        userDao.createUser(newUser);
    }

    public void updateUser (User user) {
        userDao.updateUser(user);
    }

    private UserDto transformToDto (User user) {
        return UserDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .password(user.getPassword())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    private void validateApplicantAndEmployerEmail (String employerEmail, String applicantEmail) throws CustomException{
        if (applicantEmail == null || !isUserExists(applicantEmail) ||
                !"applicant".equalsIgnoreCase(userDao.getUserByEmail(applicantEmail).get().getAccountType())) {
            throw new CustomException("Access denied");
        }

        if (employerEmail == null || !isUserExists(employerEmail) ||
                !"employer".equalsIgnoreCase(userDao.getUserByEmail(employerEmail).get().getAccountType())) {
            throw new CustomException("Access denied");
        }
    }
}
