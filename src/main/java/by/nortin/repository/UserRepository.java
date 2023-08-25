package by.nortin.repository;

import by.nortin.model.User;

public interface UserRepository {

    //    boolean checkAuthentication(String login, String password);
    boolean checkAuthentication(User user);
}
