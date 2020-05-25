package core.repository.CriteriaAPI;

import core.model.domain.Client;
import core.model.domain.Rental;
import core.repository.ClientCustomRepository;
import core.repository.CustomRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component("ClientCriteriaAPIRepoImpl")
public class ClientCriteriaAPIRepositoryImpl extends CustomRepositorySupport
        implements ClientCustomRepository {

    @Override
    public List<Client> findByAgeWithRentalAndMovie(@Param("age") int age) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        Fetch<Client, Rental> clientRentalsFetch = root.fetch(Client_.rentals, JoinType.LEFT);
        clientRentalsFetch.fetch(Rental_.movie, JoinType.LEFT);
        List<Client>  clients = entityManager.createQuery(query).getResultList();

        return clients;

    }

    @Override
    public List<Client> findByFirstName(@Param("name") String name)
    {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> Root = criteriaQuery.from(Client.class);
        ParameterExpression<String> pe = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(Root.get("name"),pe));

        TypedQuery<Client> query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(pe, name);
        List<Client> result = query.getResultList();
        return result;
    }

}
