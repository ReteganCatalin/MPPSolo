package core.repository.NativeSQL;

import core.model.domain.Movie;
import core.repository.CustomRepositorySupport;
import core.repository.MovieCustomRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("MovieNativeSQLRepoImpl")
public class MovieNativeSQLRepositoryImpl extends CustomRepositorySupport
        implements MovieCustomRepository {

    @Override
    @Transactional
    public List<Movie> findByDirectorWithRentalAndClient(@Param("director") String director) {
        Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select  {movie.*},{rental.*},{client.*} " +
                "from Movie movie " +
                "left join Rental rental on movie.id=rental.movieid " +
                "left join Client client on rental.clientid=client.id "+
                "WHERE movie.director=:director")
                .addEntity("movie",Movie.class)
                .addJoin("rental", "movie.rentals")
                .addJoin("client", "rental.client")
                .addEntity("movie",Movie.class)
                .setParameter("director",director)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Movie> movies = query.getResultList();
        return movies;
    }

    @Override
    @Transactional
    public List<Movie> findByMainStar(@Param("mainStar") String mainStar)
    {
        Session hibernateEntityManager = getEntityManager().unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("SELECT {movie.*} from Movie movie WHERE movie.mainStar=:mainStar")
                .addEntity("movie", Movie.class)
                .setParameter("mainStar",mainStar)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Movie> result = query.getResultList();
        return result;
    }

}
