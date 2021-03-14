package core.repository.NativeSQL;

import core.model.domain.Client;
import core.repository.ClientCustomRepository;
import core.repository.CustomRepositorySupport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("ClientNativeSQLRepoImpl")
public class ClientNativeSQLRepositoryImpl extends CustomRepositorySupport
        implements ClientCustomRepository {
    public static final Logger log = LoggerFactory.getLogger(ClientNativeSQLRepositoryImpl.class);
    @Override
    public List<Client> findByAgeWithRentalAndMovie(@Param("age") int age) {
        log.trace("findByAgeWithRentalAndMovie - method entered: age={}", age);
        Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
        SessionFactory hibernateSessionFactory = hibernateEntityManager.getSessionFactory();
        Session session = hibernateSessionFactory.getCurrentSession();

        org.hibernate.Query query = session.createSQLQuery("select  {client.*},{rental.*},{movie.*} " +
                "from Client client " +
                "left join Rental rental on client.id=rental.clientid " +
                "left join Movie movie on rental.movieid=movie.id "+
                "WHERE client.age=:age")
                .addEntity("client",Client.class)
                .addJoin("rental", "client.rentals")
                .addJoin("movie", "rental.movie")
                .addEntity("client",Client.class)
                .setParameter("age",age)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Client> clients = query.getResultList();
        log.trace("findByAgeWithRentalAndMovie - method finished");
        return clients;
    }

    @Override
    @Transactional
    public List<Client> findByFirstName(@Param("name") String name)
    {
        log.trace("findByFirstName- method entered");
        Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("SELECT {client.*} from Client client WHERE client.firstName=:name")
                .addEntity("client", Client.class)
                .setParameter("name",name)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Client> result = query.getResultList();
        log.trace("findByFirstName- method finished");
        return result;

    }

}
