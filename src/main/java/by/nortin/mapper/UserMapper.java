package by.nortin.mapper;

import by.nortin.dto.UserDto;
import by.nortin.model.User;

public interface UserMapper {

    User convertToModel(UserDto userDto);
}
