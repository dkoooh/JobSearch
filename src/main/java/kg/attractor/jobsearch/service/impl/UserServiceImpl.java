package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @SneakyThrows
    public User getUserByEmail(String email){
        return userDao.getUserByEmail(email).orElseThrow(() -> new CustomException("Cannot find user with ID: " + email));
    }

    public List<User> getUsersByName(String name){
        return userDao.getUsersByName(name);
    }

    public List<User> getUsersByPhoneNumber(String phoneNumber){
        return userDao.getUsersByPhoneNumber(phoneNumber);
    }

    public List<User> getApplicantsByVacancy (int vacancyId) {
        return userDao.getApplicantsByVacancy(vacancyId);
    }

    public Boolean isUserExists (String email) {
        return userDao.isUserExists(email);
    }

    public void createUser (UserDto userDto) throws CustomException{
        if (userDao.getUserByEmail(userDto.getEmail()).isPresent()) {
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
}
