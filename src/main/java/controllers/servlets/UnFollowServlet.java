package controllers.servlets;

import controllers.servlets.utils.ServletUtils;
import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "unFollowServlet", value = "/unfollow")
public class UnFollowServlet extends HttpServlet {
    UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentUserLogin = ServletUtils.getUserLoginFromSession(req);
        String userLoginToUnFollow = req.getParameter("userLoginToUnfollow");
        userDAO.stopFollowing(currentUserLogin, userLoginToUnFollow);
        req.getRequestDispatcher("users").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        super.init();
    }
}
