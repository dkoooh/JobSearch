package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
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

    public void updateUser (User user) {
        userDao.updateUser(user);
    }
}
