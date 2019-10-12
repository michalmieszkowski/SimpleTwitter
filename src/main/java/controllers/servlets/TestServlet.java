package controllers.servlets;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "test", value = "/test")
public class TestServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    User user = new User.UserBuilder().buildPassword("lala").buildEmail("w@w.pl").buildLastName("kowalik").buildName("Gabi")
        .buildLogin("Gabs").buildUser();
    req.setAttribute("user", user);
    req.getRequestDispatcher("test.jsp").forward(req, resp);
  }
}
