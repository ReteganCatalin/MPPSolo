package Service;

import Model.domain.Client;
import Model.domain.Movie;
import Model.exceptions.MyException;
import Model.exceptions.ValidatorException;
import Model.validators.MovieValidator;
import Model.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.IRepository;
import repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;


import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class MovieService implements MovieServiceInterface {
    public static final Logger log = LoggerFactory.getLogger(MovieService.class);
    @Autowired
    private MovieRepository repository;
    @Autowired
    private MovieValidator validator;


    public Optional<Movie> FindOne(Long ID)
    {
        return this.repository.findById(ID);
    }

    /**
     * Calls the repository save method with a certain Movie Object
     *
     * @param movie created movie object to be passed over to the repository
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *              if there exist already an entity with that MovieNumber
     */
    public void addMovie(Movie movie) throws ValidatorException
    {
        log.trace("addMovie - method entered: movie={}", movie);
        validator.validate(movie);
        repository.findById(movie.getId()).ifPresent(optional->{throw new MyException("Movie already exists");});
        repository.save(movie);
        log.debug("addMovie - added: movie={}", movie);
        log.trace("addMovie - method finished");
    }

    /**
     * Calls the repository update method with a certain Movie Object
     *
     * @param movie created movie object to be passed over to the repository
     * @return the updated object
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be updated.
     */
    @Override
    @Transactional
    public Movie updateMovie(Movie movie) throws ValidatorException, MyException
    {
        log.trace("updateMovie - method entered: movie={}", movie);
        validator.validate(movie);
        Movie updated=repository.findById(movie.getId()).orElseThrow(()-> new MyException("No client to update"));
        repository.findById(movie.getId())
                .ifPresent(m -> {
                    m.setDirector(movie.getDirector());
                    m.setGenre(movie.getGenre());
                    m.setMainStar(movie.getMainStar());
                    m.setTitle(movie.getTitle());
                    m.setYearOfRelease(movie.getYearOfRelease());
                    log.debug("updateMovie - updated: m={}", m);
                });
        log.trace("updateMovie - method finished");
        return updated;
    }

    /**
     * Given the id of a movie it calls the delete method of the repository with that id
     *
     * @param id the id of the movie to be deleted
     * @return the deleted Movie Instance
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be deleted.
     */
    public Movie deleteMovie(Long id) throws ValidatorException
    {
        log.trace("deleteMovie - method entered: id={}", id);
        repository.findById(id)
                .orElseThrow(()->
                        new MyException("No Movie with that id")
                );
        Movie deleted=repository.findById(id).get();
        repository.deleteById(id);
        log.debug("deleteMovie - deleted: movie={}", deleted);
        log.trace("deleteMovie - method finished");
        return deleted;
    }

    /**
     * Gets all the Movie Instances from the repository
     *
     * @return {@code Set} containing all the Movie Instances from the repository
     */
    public Set<Movie> getAllMovies()
    {
        log.trace("getAllMovies - method entered");
        Iterable<Movie> movies=repository.findAll();
        log.trace("getAllMovies - method finished");
        return StreamSupport.stream(movies.spliterator(),false).collect(Collectors.toSet());


    }

    public List<Movie> getAllMoviesSorted(Sort sort)
    {
        log.trace("getAllMoviesSorted - method entered sort={}",sort);
        Iterable<Movie> sortedMovies=repository.findAll(sort);
        log.trace("getAllMoviesSorted - method finished");
        return StreamSupport.stream(sortedMovies.spliterator(),false).collect(Collectors.toList());

    }

    /**
     * Filters all the movies out by title
     *
     * @param title a movie title of type {@code String}
     * @return {@code HashSet} containing all the Movie Instances from the repository that contain the title parameter in the title
     */
    public Set<Movie> filterMoviesByTitle(String title)
    {
        log.trace("filterMoviesByTitle - method entered title={}",title);
        Iterable<Movie> movies=repository.findAll();
        Set<Movie> filteredMovies=new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie->!(movie.getTitle().contains(title)) );
        log.trace("filterMoviesByTitle - method finished");
        return filteredMovies;
    }


    public Map<Integer, List<Movie>> statMostRichYearsInMovies(){
        log.trace("statMostRichYearsInMovies - method entered");
        List<Movie> movieList = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
        log.trace("statMostRichYearsInMovies - method finished");
        return movieList.stream()
                .collect(Collectors.groupingBy(Movie::getYearOfRelease))
                ;
    }

}
