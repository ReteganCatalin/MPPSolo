package core.Repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.model.domain.Movie;
import core.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-movie.xml")
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void findAll() throws Exception {
        List<Movie> movies = movieRepository.findAll();
        assertEquals("there should be four movies", 4, movies.size());
    }

    @Test
    @Transactional
    public void updateMovie() throws Exception {
        Movie movie=Movie.builder().yearOfRelease(2003).mainStar("Ms1").director("alex").genre("gr1").title("t1").build();
        movie.setId(1L);
        movieRepository.findById(movie.getId())
                .ifPresent(m -> {
                    m.setDirector(movie.getDirector());
                    m.setGenre(movie.getGenre());
                    m.setMainStar(movie.getMainStar());
                    m.setTitle(movie.getTitle());
                    m.setYearOfRelease(movie.getYearOfRelease());
                });
        List<Movie> movies = movieRepository.findAll();
        assertEquals("there should be four movies", movies.get(0).getDirector(),"alex");
        assertEquals("there should be four movies", 4, movies.size());
    }

    @Test
    public void createMovie() throws Exception {
        Movie movie=Movie.builder().yearOfRelease(2003).mainStar("Ms1").director("alex").genre("gr1").title("t1").build();
        movie.setId(-1L);
        movieRepository.save(movie);
        List<Movie> movies = movieRepository.findAll();
        assertEquals("there should be five movies", 5, movies.size());
    }

    @Test
    public void deleteClient() throws Exception {
        movieRepository.deleteById(1L);
        List<Movie> movies = movieRepository.findAll();
        assertEquals("there should be three movies", 3, movies.size());
    }
}
