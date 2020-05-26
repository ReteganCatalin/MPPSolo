package core.repository.CriteriaAPI;

import core.model.domain.Movie;
import core.model.domain.Movie_;
import core.model.domain.Rental;
import core.model.domain.Rental_;
import core.repository.CustomRepositorySupport;
import core.repository.MovieCustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component("MovieCriteriaAPIRepoImpl")
public class MovieCriteriaAPIRepositoryImpl extends CustomRepositorySupport
        implements MovieCustomRepository {
    public static final Logger log = LoggerFactory.getLogger(MovieCriteriaAPIRepositoryImpl.class);

    @Override
    public List<Movie> findByDirectorWithRentalAndClient(@Param("director") String director) {
        log.trace("findByDirectorWithRentalAndClient - method entered director={}",director);
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
        log.trace("findByDirectorWithRentalAndClient - method finished");

        return movies;

    }

    @Override
    public  List<Movie> findByMainStar(@Param("mainstar") String mainstar)
    {
        log.trace("findByMainStar - method entered mainStar={}",mainstar);
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> Root = criteriaQuery.from(Movie.class);
        ParameterExpression<String> pe = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(Root.get("mainStar"),pe));

        TypedQuery<Movie> query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(pe, mainstar);
        List<Movie> result = query.getResultList();
        log.trace("findByMainStar - method finished");
        return result;
    }

}
