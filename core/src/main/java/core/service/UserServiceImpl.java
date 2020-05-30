package core.service;

import core.model.domain.User;
import core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUserName(String userName) {

        return userRepository.getUserByUserName(userName);
    }
}
