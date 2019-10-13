package controllers.servlets.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    public static final String LOGIN = "login";

    public static String getUserLoginFromSession(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(LOGIN);
    }
}
