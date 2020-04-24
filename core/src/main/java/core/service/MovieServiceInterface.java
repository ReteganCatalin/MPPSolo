package core.service;

import core.model.domain.Movie;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface MovieServiceInterface {
    Optional<Movie> FindOne(Long ID);
    void addMovie(Movie movie) throws ValidatorException;
    Movie updateMovie(Movie movie) throws ValidatorException, MyException;
    Movie deleteMovie(Long id) throws ValidatorException;
    Set<Movie> getAllMovies();

    List<Movie> getAllMoviesSorted(Sort sort);
    Set<Movie> filterMoviesByTitle(String title);


    Map<Integer, List<Movie>> statMostRichYearsInMovies();
}