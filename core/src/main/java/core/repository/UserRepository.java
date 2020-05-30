package core.repository;

import core.model.domain.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends IRepository<User, Long> {

    @Query("select u from User u where u.userName=?1")
    User getUserByUserName(String userName);
}
