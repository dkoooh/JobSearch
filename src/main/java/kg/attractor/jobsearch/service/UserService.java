package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.User;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email);

    List<User> getUsersByName(String name);

    List<User> getUsersByPhoneNumber(String phoneNumber);

    List<User> getApplicantsByVacancy (int vacancyId);

    Boolean isUserExists (String email);

    void createUser (UserDto userDto) throws CustomException;

    void updateUser (User user);
}
