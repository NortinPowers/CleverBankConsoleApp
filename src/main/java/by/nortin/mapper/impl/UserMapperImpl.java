package by.nortin.mapper.impl;

import by.nortin.dto.UserDto;
import by.nortin.mapper.UserMapper;
import by.nortin.model.User;
import by.nortin.util.InjectObjectsFactory;
import org.modelmapper.ModelMapper;

public class UserMapperImpl implements UserMapper {

    private final ModelMapper modelMapper;

    {
        modelMapper = (ModelMapper) InjectObjectsFactory.getInstance(ModelMapper.class);
    }

    /**
     * Implementation of a method that converts the UserDto object to the User object.
     *
     * @param userDto - object to convert
     * @return User object
     */
    @Override
    public User convertToModel(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
