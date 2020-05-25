package core.repository.NativeSQL;

import core.model.domain.Client;
import core.repository.ClientCustomRepository;
import core.repository.CustomRepositorySupport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ClientNativeRepoImpl")
public class ClientNativeSQLRepositoryImpl extends CustomRepositorySupport
        implements ClientCustomRepository {

    @Override
    public List<Client> findByAgeWithRentalAndMovie(@Param("age") int age) {
        Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select  {client.*},{rentals.*},{movie.*} " +
                "from Client client " +
                "left join Rental rental on client.id=rental.client_id " +
                "left join Movie movie on rental.movie_id=movie.id "+
                "WHERE client.age=:age")
                .addEntity("Client",Client.class)
                .addJoin("Rental", "client.rentals")
                .addJoin("Movie", "rental.movie")
                .addEntity("Client",Client.class)
                .setParameter("age",age)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Client> clients = query.getResultList();
        return clients;
    }

    @Override
    public List<Client> findByFirstName(@Param("name") String name)
    {
        Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("SELECT {client.*} from clients client WHERE client.firstName=:name").addEntity("client", Client.class).setParameter("name",name).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Client> result = query.getResultList();
        return result;
    }

}
