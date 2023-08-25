package by.nortin.service;

import by.nortin.dto.UserDto;

public interface UserService {

    //    boolean checkAuthentication(String login, String password);
    boolean checkAuthentication(UserDto userDto);
}
