package web.controller;

import core.model.domain.Movie;
import core.service.MovieService;
import core.service.MovieServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.MovieConverter;
import web.dto.*;
import web.dto.MoviesDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    public static final Logger log= LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieServiceInterface movieService;

    @Autowired
    private MovieConverter movieConverter;


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MoviesDto getMovies() {
        log.trace("Method getMovies entered");
        return new MoviesDto(movieConverter
                .convertModelsToDtos(new ArrayList<>(movieService.getAllMovies())));

    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    void saveMovie(@RequestBody MovieDto movieDto) {
        log.trace("Method saveMovie entered with movieDto = {}",movieDto);
        movieService.addMovie(movieConverter.convertDtoToModel(movieDto));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.PUT)
    MovieDto updateMovie(@RequestBody MovieDto movieDto) {
        log.trace("Method updateMovie entered with movieDto {}",movieDto);
        return movieConverter.convertModelToDto( movieService.updateMovie(
                movieConverter.convertDtoToModel(movieDto)));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id){
        log.trace("Method deleteMovie entered with id {}",id);

        movieService.deleteMovie(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value ="/sortMovies",method=RequestMethod.POST )
    MoviesDto getSortedMovies(@RequestBody SortDto sorted)
    {
        log.trace("Method getSortedMovies entered");
        List<String> directions=sorted.getDirections();
        List<String> columns=sorted.getColumns();
        Sort sort=null;
        if(directions.get(0).equals("Asc")) {
            sort = new Sort(columns.get(0)).ascending();
        }
        else
        {
            sort = new Sort(columns.get(0)).descending();
        }
        for(int index=1;index<directions.size();index++)
        {
            if(directions.get(index).equals("Asc")) {
                sort.and(new Sort(columns.get(index)).ascending());
            }
            else
            {
                sort.and(new Sort(columns.get(index)).descending());
            }
        }
        log.trace("Method getMoviesSorted sort {} created", sort);
        return new MoviesDto(movieConverter.convertModelsToDtos(movieService.getAllMoviesSorted(sort)));
    }

    @RequestMapping(value = "/filterMovies/{title}", method=RequestMethod.GET)
    MoviesDto getFilteredMovies(@PathVariable String title)
    {
        log.trace("Method getFilteredMovies entered with Path Variable: title {}"+title);
        return new MoviesDto(movieConverter
                .convertModelsToDtos(movieService.filterMoviesByTitle(title)));
    }

    @RequestMapping(value= "/statMovies",method=RequestMethod.GET)
    StatMoviesDto getStatMovies()
    {
        log.trace("Method getStatMovies entered");
        List<StatMovieDto> richYears = movieService.statMostRichYearsInMovies()
                .entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().size() - o1.getValue().size())
               .map(integerListEntry -> {
                           Integer year = integerListEntry.getKey();
                           List<String> movies = integerListEntry.getValue().stream().map(Movie::getTitle).collect(Collectors.toList());
                           return StatMovieDto.builder().year(year).titles(movies).build();
                       }
                ).collect(Collectors.toList());
        return StatMoviesDto.builder().movies(richYears).build() ;
    }
}