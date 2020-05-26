package core.repository.CriteriaAPI;

import core.model.domain.*;
import core.repository.CustomRepositorySupport;
import core.repository.MovieCustomRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component("MovieCriteriaAPIRepoImpl")
public class MovieCriteriaAPIRepositoryImpl extends CustomRepositorySupport
        implements MovieCustomRepository {

    @Override
    public List<Movie> findByDirectorWithRentalAndClient(@Param("director") String director) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = query.from(Movie.class);
        Fetch<Movie, Rental> movieRentalsFetch = root.fetch(Movie_.rentals, JoinType.LEFT);
        movieRentalsFetch.fetch(Rental_.movie, JoinType.LEFT);
        ParameterExpression<String> directing = criteriaBuilder.parameter(String.class);
        query.where(criteriaBuilder.equal(root.get("director"),directing));

        TypedQuery<Movie> querying = getEntityManager().createQuery(query);
        querying.setParameter(directing,director);

        List<Movie>  movies = querying.getResultList();

        return movies;

    }

    @Override
    public  List<Movie> findByMainStar(@Param("mainstar") String mainstar)
    {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> Root = criteriaQuery.from(Movie.class);
        ParameterExpression<String> pe = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(Root.get("mainStar"),pe));

        TypedQuery<Movie> query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(pe, mainstar);
        List<Movie> result = query.getResultList();
        return result;
    }

}
