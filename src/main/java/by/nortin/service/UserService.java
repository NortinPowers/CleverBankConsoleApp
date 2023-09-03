package by.nortin.service;

import by.nortin.dto.UserDto;

public interface UserService {

    /**
     * The method verifies user authentication.
     *
     * @param userDto UserDto
     * @return boolean result
     */
    boolean checkAuthentication(UserDto userDto);
}
