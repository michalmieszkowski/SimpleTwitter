package dao;

import model.User;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAO extends AbstractDAO {

    public void saveUser(User user) {
        hibernateUtil.save(user);
    }

    public void deleteUser(long userId) {
        hibernateUtil.delete(User.class, userId);
    }

    public User getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u from User u WHERE email = :email", User.class);
        return query.setParameter("email", email).getSingleResult();
    }

    public User getUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u from User u WHERE login = :login", User.class);
        return query.setParameter("login", login).getSingleResult();
    }

    public List<User> getUserByName(String name) {
        Query query = entityManager.createQuery("SELECT u from User u WHERE name = :name");
        return query.setParameter("name", name).getResultList();
    }

    public boolean isUserValid(String login, String password) {
        Query query = entityManager.createQuery("SELECT count(*) as cnt from User u WHERE u.login = :login and u.password = :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        Object singleResult = query.getSingleResult();
        return ((Long) singleResult > 0) ? true : false;
    }

    public List<User> getFollowedUsers(String login) {
        User user = getUserByLogin(login);
        Long userId = user.getId();
        Query query = entityManager.createQuery("select follows from User u where u.id = :userId");
        return query.setParameter("userId", userId).getResultList();
    }

    public List<User> getNotFollowedUsers(String login) {
        Query query = entityManager.createQuery("select u from User u where u.login != :login");
        query.setParameter("login", login);
        //TODO implement query with left join relation to follows_followed table
        List<User> users = query.getResultList();
        List<User> followedUsers = getFollowedUsers(login);
        users.removeAll(followedUsers);
        return users;
    }

    public void follow(String currentUserLogin, String userLoginToFollow) {
        if (currentUserLogin != userLoginToFollow) {
            User currentUser = getUserByLogin(currentUserLogin);
            User userToFollow = getUserByLogin(userLoginToFollow);
            currentUser.getFollows().add(userToFollow);
            saveUser(currentUser);
        }
    }

    public void stopFollowing(String currentUserLogin, String userLoginToStopFollow) {
        if (currentUserLogin != userLoginToStopFollow) {
            User currentUser = getUserByLogin(currentUserLogin);
            User userToStopFollowing = getUserByLogin(userLoginToStopFollow);
            currentUser.getFollows().remove(userToStopFollowing);
            saveUser(currentUser);
        }
    }

    public List<User> getAllUsers() {
        TypedQuery typedQuery = entityManager.createQuery("SELECT u from User u", User.class);
        return typedQuery.getResultList();
    }

}
