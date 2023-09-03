package by.nortin.service.impl;

import static by.nortin.util.InjectObjectsFactory.getInstance;

import by.nortin.dto.UserDto;
import by.nortin.mapper.UserMapper;
import by.nortin.model.User;
import by.nortin.repository.UserRepository;
import by.nortin.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    {
        userRepository = (UserRepository) getInstance(UserRepository.class);
        userMapper = (UserMapper) getInstance(UserMapper.class);
    }

    /**
     * Implementation of the method verifies user authentication.
     *
     * @param userDto UserDto
     * @return boolean result
     */
    @Override
    public boolean checkAuthentication(UserDto userDto) {
        User user = userMapper.convertToModel(userDto);
        return userRepository.checkAuthentication(user);
    }
}
