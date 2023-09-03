package by.nortin.mapper;

import by.nortin.dto.UserDto;
import by.nortin.model.User;

public interface UserMapper {

    /**
     * The method converts the UserDto object to the User object.
     *
     * @param userDto - object to convert
     * @return User object
     */
    User convertToModel(UserDto userDto);
}
