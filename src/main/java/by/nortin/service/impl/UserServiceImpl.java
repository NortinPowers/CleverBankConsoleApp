package by.nortin.service.impl;

import by.nortin.repository.UserRepository;
import by.nortin.service.UserService;
import by.nortin.util.InjectObjectsFactory;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    {
        userRepository = (UserRepository) InjectObjectsFactory.getInstance(UserRepository.class);
    }

    @Override
    public boolean checkAuthentication(String login, String password) {
        //test
//        if ("nortin".equals(login) && "12345".equals(password)) {
//            return true;
//        }
//        return false;
        return userRepository.checkAuthentication(login, password);
    }
}
