package core.repository;

import core.model.domain.Movie;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieCustomRepository {
    List<Movie> findByDirectorWithRentalAndClient(@Param("director") String director);

    List<Movie> findByMainStar(@Param("mainstar") String mainStar);
}
