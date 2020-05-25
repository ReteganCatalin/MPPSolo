package core.repository;

import core.model.domain.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends IRepository<Client, Long> {

    @Query("select client from Client client")
    @EntityGraph(value = "clientWithRentalsAndMovie", type =
            EntityGraph.EntityGraphType.FETCH)
    List<Client> findAllWithRentals();

    @Query("select client from Client client where client.id=:clientID")
    @EntityGraph(value = "clientWithRentalsAndMovie", type =
            EntityGraph.EntityGraphType.FETCH)
    Optional<Client> findByIDWithRentals(@Param("clientID") Long clientID);

    @Query("select client from Client client where client.firstName=:name or client.lastName=:name")
    @EntityGraph(value = "clientWithRentalsAndMovie", type =
            EntityGraph.EntityGraphType.FETCH)
    List<Client> findClientsByName(@Param("name") String name);





}
