package kg.attractor.jobsearch.util;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.exception.CustomException;

public class Utils {
    private Utils() {
    }

    public static void verifyUser (String email, String necessaryAccountType, UserDao userDao) throws CustomException {
        if (email == null || !userDao.isUserExists(email) ||
                !necessaryAccountType.equalsIgnoreCase(userDao.getUserByEmail(email).get().getAccountType())) {
            throw new CustomException("Access denied");
        }
    }

    public static void verifyUser (String email, UserDao userDao) throws CustomException {
        if (email == null || !userDao.isUserExists(email)) {
            throw new CustomException("Access denied");
        }
    }
}
