package core.repository;


import core.model.domain.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("MovieNativeSQLRepo")
public interface MovieRepository extends IRepository<Movie, Long>, MovieCustomRepository {

    @Query("select movie from Movie movie")
    @EntityGraph(value = "movieWithRentalsAndClient", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllWithRentals();

    @Query("select movie from Movie movie where movie.title=:title")
    @EntityGraph(value = "movieWithRentalsAndClient", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllMovieByTitle(@Param("title") String title);

    @Query("select movie from Movie movie where movie.id=:movieID")
    @EntityGraph(value = "movieWithRentalsAndClient", type =
            EntityGraph.EntityGraphType.LOAD)
    Optional<Movie> findByIDWithRentals(@Param("movieID") Long movieID);
}
