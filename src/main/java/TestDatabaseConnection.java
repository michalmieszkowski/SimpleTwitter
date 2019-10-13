//import java.util.Date;
//
//public class TestDatabaseConnection {
//
//
//    public static void main(String[] args) {
//        HibernateUtil hibernateUtil = HibernateUtil.getInstance();
//        UserDAO userDAO = new UserDAO();
//
//        for (int i = 0; i < 20; i++) {
//            User user = new User();
//            user.setDateOfRegistration(new Date());
//            user.setLogin("Login" + i);
//            user.setName("Name");
//            user.setLastName("LastName");
//            user.setPassword("pass");
//            user.setEmail("email@o" + i);
//            userDAO.saveUser(user);
//
//        }
//
//
//    }
//}
