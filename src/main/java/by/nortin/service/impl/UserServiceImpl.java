package by.nortin.service.impl;

import by.nortin.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public boolean checkAuthentificate(String login, String password) {
        //test
        if ("nortin".equals(login) && "12345".equals(password)) {
            return true;
        }

        return false;
    }
}
