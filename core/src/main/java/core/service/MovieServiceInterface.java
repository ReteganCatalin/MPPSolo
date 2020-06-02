package core.service;

import core.model.domain.Movie;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieServiceInterface {
    Optional<Movie> FindOne(Long ID);
    void addMovie(Movie movie) throws ValidatorException;
    Movie updateMovie(Movie movie) throws ValidatorException, MyException;
    Movie deleteMovie(Long id) throws ValidatorException;
    List<Movie> getAllMovies();

    List<Movie> getAllMoviesSorted(Sort sort);
    List<Movie> filterMoviesByTitle(String title);
    List<Movie> filterMoviesByDirector(String director);
    List<Movie> filterMoviesByMainStar(String mainStar);
    List<Movie> paginatedMovies(Integer pageNo,Integer size);

    Map<Integer, List<Movie>> statMostRichYearsInMovies();
}
