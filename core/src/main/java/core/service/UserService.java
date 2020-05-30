package core.service;

import core.model.domain.User;

public interface UserService {

    User getUserByUserName(String userName);
}
