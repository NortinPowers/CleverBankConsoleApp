package by.nortin.mapper.impl;

import by.nortin.dto.UserDto;
import by.nortin.mapper.UserMapper;
import by.nortin.model.User;

public class UserMapperImpl implements UserMapper {

    @Override
    public User convertToModel(UserDto userDto) {
        User user = new User();
        if (userDto.getLogin() != null) {
            user.setLogin(userDto.getLogin());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        return user;
    }
}
