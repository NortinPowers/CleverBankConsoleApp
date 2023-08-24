package by.nortin.repository;

public interface UserRepository {

    boolean checkAuthentication(String login, String password);
}
