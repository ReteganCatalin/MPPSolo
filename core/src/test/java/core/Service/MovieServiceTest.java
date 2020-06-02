package core.Service;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.model.domain.Movie;
import core.service.MovieServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-movie.xml")
public class MovieServiceTest {

    @Autowired
    private MovieServiceInterface movieService;

    @Test
    public void findAll() throws Exception {
        List<Movie> movies = movieService.getAllMovies();
        assertEquals("there should be four clients", 4, movies.size());
    }

    @Test
    public void updateMovie() throws Exception {
        Movie movie=Movie.builder().title("t1").director("Catalin").mainStar("alex").genre("g1").yearOfRelease(2002).build();
        movie.setId(1L);
        movieService.updateMovie(movie);
        List<Movie> movies = movieService.getAllMovies();
        assertEquals("The director should be Catalin", movies.get(0).getDirector(),"Catalin");
        assertEquals("there should be four movies", 4, movies.size());
    }

    @Test
    public void createMovie() throws Exception {
        Movie movie=Movie.builder().title("t1").director("Catalin").mainStar("alex").genre("g1").yearOfRelease(2002).build();
        movie.setId(5L);
        movieService.addMovie(movie);
        List<Movie> movies = movieService.getAllMovies();
        assertEquals("there should be five clients", 5, movies.size());
    }

    @Test
    public void deleteClient() throws Exception {
        movieService.deleteMovie(1L);
        List<Movie> movies = movieService.getAllMovies();
        assertEquals("there should be three clients", 3, movies.size());
    }

}
