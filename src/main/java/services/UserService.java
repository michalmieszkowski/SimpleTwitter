package services;

import dao.UserDAO;
import model.User;

import javax.persistence.NoResultException;
import java.util.Date;

public class UserService {

    public static final String EMAIL_ERROR = "Email is already in use!";
    public static final String LOGIN_ERROR = "Login is already in use!";
    public static final String SUCCESS = "Success!=)";
    public static final String EMAIL_AND_LOGGIN_ERROR = "Email is already in use and login is already in use!";


    UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String registerUser(User user) {
        if (isUserEmailExist(user.getEmail()) && isUserLoginExist(user.getLogin())) {
            return EMAIL_AND_LOGGIN_ERROR;
        } else if (isUserLoginExist(user.getLogin())) {
            return LOGIN_ERROR;
        } else if (isUserEmailExist(user.getEmail())) {
            return EMAIL_ERROR;
        } else {
            user.setDateOfRegistration(new Date());
            userDAO.saveUser(user);
            return SUCCESS;
        }
    }

    private boolean isUserLoginExist(String login) {
        try {
            userDAO.getUserByLogin(login);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    private boolean isUserEmailExist(String email) {
        try {
            userDAO.getUserByEmail(email);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }


}
