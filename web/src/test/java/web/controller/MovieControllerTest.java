package web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.model.domain.Movie;
import core.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web.converter.MovieConverter;
import web.dto.MovieDto;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieConverter movieConverter;

    private Movie movie1;
    private Movie movie2;
    private MovieDto movieDto1;
    private MovieDto movieDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(movieController)
                .build();
        initData();
    }

    @Test
    public void getMovies() throws Exception {
        List<Movie> movies = Arrays.asList(movie1, movie2);

        List<MovieDto> movieDtos =
                new ArrayList<>(Arrays.asList(movieDto1, movieDto2));
        when(movieService.getAllMovies()).thenReturn(movies);
        when(movieConverter.convertModelsToDtos(movies)).thenReturn(movieDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].director", anyOf(is("d1"), is("d2"))))
                .andExpect(jsonPath("$[1].genre", anyOf(is("g1"), is("g2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println(result);

        verify(movieService, times(1)).getAllMovies();
        verify(movieConverter, times(1)).convertModelsToDtos(movies);
        verifyNoMoreInteractions(movieService, movieConverter);


    }

    @Test
    public void updateMovie() throws Exception {

        when(movieService.updateMovie(movie1))
                .thenReturn(movie1);

        when(movieConverter.convertModelToDto(any(Movie.class))).thenReturn(movieDto1);
        when(movieConverter.convertDtoToModel(any(MovieDto.class))).thenReturn(movie1);

        Map<String, MovieDto> movieDtoMap = new HashMap<>();
        movieDtoMap.put("movie", movieDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/movies", movie1.getId(), movieDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(movieDto1)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.director", is("d1")));

        verify(movieService, times(1)).updateMovie(movie1);
        verify(movieConverter, times(1)).convertModelToDto(any(Movie.class));
        verify(movieConverter, times(1)).convertDtoToModel(any(MovieDto.class));
        verifyNoMoreInteractions(movieService, movieConverter);
    }

    private String toJsonString(Map<String, MovieDto> movieDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(movieDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString(MovieDto movieDto) {
        try {
            return new ObjectMapper().writeValueAsString(movieDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createMovie() throws Exception {
        when(movieConverter.convertDtoToModel(any(MovieDto.class))).thenReturn(movie1);
        ResultActions resultActions =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders
                                        .post("/movies", movie1)
                                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                                        .content(toJsonString(movieDto1)))
                        .andExpect(status().isOk());
        verify(movieService, times(1)).addMovie(movie1);
        verify(movieConverter, times(1)).convertDtoToModel(any(MovieDto.class));
        verifyNoMoreInteractions(movieService,movieConverter);

    }

    @Test
    public void deleteMovie() throws Exception {


        ResultActions resultActions =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders
                                        .delete("/movies/{Id}", movie1.getId())
                                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk());


        verify(movieService, times(1)).deleteMovie(movie1.getId());
        verifyNoMoreInteractions(movieService,movieConverter);

    }

    private void initData() {
        movie1 = Movie.builder().yearOfRelease(2003).genre("g1").title("t1").mainStar("mS1").director("d1").build();
        movie1.setId(1l);
        movie2 = Movie.builder().yearOfRelease(2003).genre("g2").title("t2").mainStar("mS2").director("d2").build();
        movie2.setId(2l);

        movieDto1 = createMovieDto(movie1);
        movieDto2 = createMovieDto(movie2);

    }

    private MovieDto createMovieDto(Movie movie) {
        MovieDto movieDto = MovieDto.builder()
                .director(movie.getDirector())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .mainStar(movie.getMainStar())
                .yearOfRelease(movie.getYearOfRelease())
                .build();
        movieDto.setId(movie.getId());
        return movieDto;
    }


}
