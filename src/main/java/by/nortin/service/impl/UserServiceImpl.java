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

    @Override
//    public boolean checkAuthentication(String login, String password) {
    public boolean checkAuthentication(UserDto userDto) {
        //test
//        if ("nortin".equals(login) && "12345".equals(password)) {
//            return true;
//        }
//        return false;

//        User user = new User();
//        user.setLogin(userDto.getLogin());
//        user.setPassword(userDto.getPassword());

        User user = userMapper.convertToModel(userDto);
//        return userRepository.checkAuthentication(userDto.getLogin(), userDto.getPassword());
        return userRepository.checkAuthentication(user);
    }
}
