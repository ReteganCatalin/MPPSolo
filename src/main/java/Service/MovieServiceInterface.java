package Service;

import Model.domain.Movie;
import Model.exceptions.MyException;
import Model.exceptions.ValidatorException;
import org.springframework.data.domain.Sort;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
