package core.repository;

import core.model.domain.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("ClientJPQLRepository")
public interface ClientRepository extends IRepository<Client, Long> , ClientCustomRepository{

    @Query("select client from Client client")
    @EntityGraph(value = "clientWithRentalsAndMovie", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithRentals();

    @Query("select client from Client client where client.id=:clientID")
    @EntityGraph(value = "clientWithRentalsAndMovie", type =
            EntityGraph.EntityGraphType.LOAD)
    Optional<Client> findByIDWithRentals(@Param("clientID") Long clientID);

    @Query("select client from Client client where client.firstName=:name or client.lastName=:name")
    @EntityGraph(value = "clientWithRentalsAndMovie", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Client> findClientsByName(@Param("name") String name);





}
