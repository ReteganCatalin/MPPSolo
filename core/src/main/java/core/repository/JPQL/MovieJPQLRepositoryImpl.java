package core.repository.JPQL;

import core.model.domain.Movie;
import core.repository.CustomRepositorySupport;
import core.repository.MovieCustomRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MovieJPQLRepositoryImpl extends CustomRepositorySupport
        implements MovieCustomRepository {

    @Override
    public List<Movie> findByDirectorWithRentalAndClient(@Param("director") String director) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select movie from Movie movie " +
                        "left join fetch movie.rentals rent " +
                        "left join fetch rent.client client "+
                        "where movie.director=:director"

        );
        List<Movie> movies = query.getResultList();

        return movies;
    }

    @Override
    public List<Movie> findByMainStar(@Param("mainstar") String mainstar)
    {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select movie from Movie movie " +
                        "where movie.mainStar=:mainstar"

        );
        List<Movie> movies = query.getResultList();

        return movies;
    }

}
