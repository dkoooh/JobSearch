package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.User;

import java.util.List;

public interface UserService {

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByName(String name);

    List<UserDto> getUsersByPhoneNumber(String phoneNumber);

    List<UserDto> getApplicantsByVacancy (int vacancyId);

    UserDto getEmployer (String employerEmail, String applicantEmail) throws CustomException;

    UserDto getApplicant (String employerEmail, String applicantEmail) throws CustomException;

    Boolean isUserExists (String email);

    void createUser (UserDto userDto) throws CustomException;

    void updateUser (User user);
}
