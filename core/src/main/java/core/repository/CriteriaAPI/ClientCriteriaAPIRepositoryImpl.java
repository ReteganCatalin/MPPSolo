package core.repository.CriteriaAPI;

import core.model.domain.Client;
import core.model.domain.Client_;
import core.model.domain.Rental;
import core.model.domain.Rental_;
import core.repository.ClientCustomRepository;
import core.repository.CustomRepositorySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component("ClientCriteriaAPIRepoImpl")
public class ClientCriteriaAPIRepositoryImpl extends CustomRepositorySupport
        implements ClientCustomRepository {
    public static final Logger log = LoggerFactory.getLogger(ClientCriteriaAPIRepositoryImpl.class);

    @Override
    public List<Client> findByAgeWithRentalAndMovie(@Param("age") int age) {
        log.trace("findByAgeWithRentalAndMovie - method entered: age={}", age);
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        query.distinct(Boolean.TRUE);
        Root<Client> root = query.from(Client.class);
        Fetch<Client, Rental> clientRentalsFetch = root.fetch(Client_.rentals, JoinType.LEFT);
        clientRentalsFetch.fetch(Rental_.movie, JoinType.LEFT);
        ParameterExpression<Integer> ageing = criteriaBuilder.parameter(Integer.class);
        query.where(criteriaBuilder.equal(root.get("age"),ageing));

        TypedQuery<Client> querying = getEntityManager().createQuery(query);
        querying.setParameter(ageing,age);

        List<Client>  clients = querying.getResultList();
        log.trace("findByAgeWithRentalAndMovie - method finished");

        return clients;

    }

    @Override
    public List<Client> findByFirstName(@Param("name") String name)
    {
        log.trace("findByFirstName- method entered");
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> Root = criteriaQuery.from(Client.class);
        ParameterExpression<String> pe = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(Root.get("firstName"),pe));

        TypedQuery<Client> query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(pe, name);
        List<Client> result = query.getResultList();
        log.trace("findByFirstName- method finished");
        return result;
    }

}
