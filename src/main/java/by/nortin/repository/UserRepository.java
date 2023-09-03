package by.nortin.repository;

import by.nortin.model.User;

public interface UserRepository {

    /**
     * The method verifies user authentication.
     *
     * @param user User
     * @return boolean result
     */
    boolean checkAuthentication(User user);
}
