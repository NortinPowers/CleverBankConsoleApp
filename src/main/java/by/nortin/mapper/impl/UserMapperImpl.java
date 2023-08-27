package by.nortin.mapper.impl;

import by.nortin.dto.UserDto;
import by.nortin.mapper.UserMapper;
import by.nortin.model.User;
import by.nortin.util.InjectObjectsFactory;
import by.nortin.util.MapperUtils;
import org.modelmapper.ModelMapper;

public class UserMapperImpl implements UserMapper {

    private final ModelMapper modelMapper;

    {
        modelMapper = (ModelMapper) InjectObjectsFactory.getInstance(ModelMapper.class);
    }

    @Override
    public User convertToModel(UserDto userDto) {
//        User user = new User();
//        if (userDto.getLogin() != null) {
//            user.setLogin(userDto.getLogin());
//        }
//        if (userDto.getPassword() != null) {
//            user.setPassword(userDto.getPassword());
//        }
//        return user;
//        return MapperUtils.setValue(userDto, user);
        return modelMapper.map(userDto, User.class);
    }
}
