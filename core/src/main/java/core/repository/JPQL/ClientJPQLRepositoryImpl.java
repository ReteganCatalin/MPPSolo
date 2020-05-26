package core.repository.JPQL;

import core.model.domain.Client;
import core.repository.ClientCustomRepository;
import core.repository.CustomRepositorySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("ClientJPQLRepoImpl")
public class ClientJPQLRepositoryImpl extends CustomRepositorySupport
        implements ClientCustomRepository  {

    public static final Logger log = LoggerFactory.getLogger(ClientJPQLRepositoryImpl.class);

    @Override
    public List<Client> findByAgeWithRentalAndMovie(@Param("age") int age) {
        log.trace("findByAgeWithRentalAndMovie - method entered: age={}", age);
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct client from Client client " +
                        "left join fetch client.rentals rents " +
                        "left join fetch rents.movie movie " +
                        "where client.age=:age"

        );
        query.setParameter("age",age);
        List<Client> clients = query.getResultList();
        log.trace("findByAgeWithRentalAndMovie - method finished");
        return clients;
    }

    @Override
    public List<Client> findByFirstName(@Param("name") String name)
    {
        log.trace("findByFirstName- method entered");
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select client from Client client " +
                        "where client.firstName=:name"

        );
        query.setParameter("name",name);
        List<Client> clients = query.getResultList();
        log.trace("findByFirstName- method finished");

        return clients;
    }

}
