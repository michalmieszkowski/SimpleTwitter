package controllers.servlets;

import controllers.servlets.utils.ServletUtils;
import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = {"", "/login"})
public class LoginServlet extends HttpServlet {


    private final String PASSWORD = "password";
    private final String REMEMBER = "remember";
    private final String LOGIN_COOKIE = "twitter_login";
    private final String PASSWORD_COOKIE = "twitter_cookie";
    private final int SECONDS_IN_DAY = 60 * 60 * 60 * 24;             //zywotnosc cookie .. np 1dzien
    private final String CHECKBOX_SELECTED = "on";

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        super.init();
    }

    /**
     * if user send request:
     * Cookies will be checked if any of Logging depended are stored in user browser
     * if those cookies exist, their max age will by extended. Login and password would be attached to request as a attribute
     * and doPost method will be called
     * otherwise login.jsp will be displayed
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = null;
        String password = null;

        if (null != req.getCookies()) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(LOGIN_COOKIE)) {
                    login = cookie.getValue();
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    resp.addCookie(cookie);
                } else if (cookie.getName().equals(PASSWORD_COOKIE)) {
                    password = cookie.getValue();
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    resp.addCookie(cookie);
                }
            }
        }
        if (null != login && null != password) {
            req.setAttribute(ServletUtils.LOGIN, login);
            req.setAttribute(PASSWORD, password);
            doPost(req, resp);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    /**
     * begin, method try to get login, password from parameters (parameters are send by form)
     * if parameters are null it means that doPost method was called by doGet(), because doGet method is putting attributes
     * instead of parameters,
     * in next step attributes are checked and login and password are set to attributes values
     * Login and password are verified by dao isUserValid method. If is valid, login is saved in session attribute
     * if remember checkbox is set, cookies for login and password are created and attached to response
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(ServletUtils.LOGIN);
        String password = req.getParameter(PASSWORD);
        String remember = req.getParameter(REMEMBER);

        if (null == login || null == password) {
            login = (String) req.getAttribute(ServletUtils.LOGIN);
            password = (String) req.getAttribute(PASSWORD);
        }

        if (userDAO.isUserValid(login, password)) {
            req.getSession().setAttribute(ServletUtils.LOGIN, login);
            if (null != remember && remember.equals(CHECKBOX_SELECTED)) {
                Cookie loginCookie = new Cookie(LOGIN_COOKIE, login);
                Cookie passwordCookie = new Cookie(PASSWORD_COOKIE, password);
                loginCookie.setMaxAge(SECONDS_IN_DAY);
                passwordCookie.setMaxAge(SECONDS_IN_DAY);
                resp.addCookie(loginCookie);
                resp.addCookie(passwordCookie);
            }
            req.getRequestDispatcher("users").forward(req, resp);      //przekierowujemy na servlet a nie statyczna strone np. (/login)
        } else {
            req.setAttribute("hasError", "true");
            req.setAttribute("error", "Login or password incorrect");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }

    }

}
