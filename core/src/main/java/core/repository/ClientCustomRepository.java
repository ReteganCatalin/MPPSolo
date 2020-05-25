package core.repository;

import core.model.domain.Client;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientCustomRepository {
    List<Client> findByAgeWithRentalAndMovie(@Param("age") int age);

    List<Client> findByFirstName(@Param("name") String name);
}
