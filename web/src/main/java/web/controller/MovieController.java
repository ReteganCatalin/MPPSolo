package web.controller;

import core.model.domain.Movie;
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
    List<MovieDto> getMovies() {
        log.trace("Method getMovies entered");
        return movieConverter
                .convertModelsToDtos(new ArrayList<>(movieService.getAllMovies()));

    }
    @CrossOrigin
    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    void saveMovie(@RequestBody MovieDto movieDto) {
        log.trace("Method saveMovie entered with movieDto = {}",movieDto);
        movieService.addMovie(movieConverter.convertDtoToModel(movieDto));
    }
    @CrossOrigin
    @RequestMapping(value = "/movies", method = RequestMethod.PUT)
    MovieDto updateMovie(@RequestBody MovieDto movieDto) {
        log.trace("Method updateMovie entered with movieDto {}",movieDto);
        return movieConverter.convertModelToDto( movieService.updateMovie(
                movieConverter.convertDtoToModel(movieDto)));
    }
    @CrossOrigin
    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id){
        log.trace("Method deleteMovie entered with id {}",id);

        movieService.deleteMovie(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value ="/sortMovies",method=RequestMethod.POST )
    List<MovieDto> getSortedMovies(@RequestBody SortDto sorted)
    {
        log.trace("Method getSortedMovies entered");
        List<SortObjectDTO> sorts=sorted.getSort();
        Sort sort=null;
        if(sorts.get(0).getDirection().equals("Asc")) {
            sort = new Sort(sorts.get(0).getColumn()).ascending();
        }
        else
        {
            sort = new Sort(sorts.get(0).getColumn()).descending();
        }
        for(int index=1;index<sorts.size();index++)
        {
            if(sorts.get(index).getDirection().equals("Asc")) {
                sort=sort.and(new Sort(sorts.get(index).getColumn()).ascending());
            }
            else
            {
                sort=sort.and(new Sort(sorts.get(index).getColumn()).descending());
            }
        }
        log.trace("Method getMoviesSorted sort {} created", sort);
        return movieConverter.convertModelsToDtos(movieService.getAllMoviesSorted(sort));
    }
    @CrossOrigin
    @RequestMapping(value = "/filterMovies/{title}", method=RequestMethod.GET)
    List<MovieDto> getFilteredMovies(@PathVariable String title)
    {
        log.trace("Method getFilteredMovies entered with Path Variable: title {}"+title);
        return movieConverter
                .convertModelsToDtos(movieService.filterMoviesByTitle(title));
    }
    @CrossOrigin
    @RequestMapping(value = "/movies/get-page/pageno={pageNo},size={size}",method=RequestMethod.GET)
    List<MovieDto> getPaginatedMovies(@PathVariable Integer pageNo,@PathVariable Integer size)
    {
        log.trace("Method getPaginatedMovies entered with Path Variable: pageNo {} and size {}",pageNo,size);
        return movieConverter
                .convertModelsToDtos(movieService.paginatedMovies(pageNo,size));
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
