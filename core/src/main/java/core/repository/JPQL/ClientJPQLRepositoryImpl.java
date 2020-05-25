package core.repository.JPQL;

import core.model.domain.Client;
import core.repository.ClientCustomRepository;
import core.repository.CustomRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("ClientJPQLRepoImpl")
public class ClientJPQLRepositoryImpl extends CustomRepositorySupport
        implements ClientCustomRepository  {

    @Override
    public List<Client> findByAgeWithRentalAndMovie(@Param("age") int age) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select client,movie.director from Client client " +
                        "left join fetch client.rentals rents " +
                        "left join fetch rents.movie movie " +
                        "where client.age=:age"

        );
        List<Client> clients = query.getResultList();

        return clients;
    }

    @Override
    public List<Client> findByFirstName(@Param("name") String name)
    {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select client from Client client " +
                        "where client.firstName=:age"

        );
        List<Client> clients = query.getResultList();

        return clients;
    }

}
