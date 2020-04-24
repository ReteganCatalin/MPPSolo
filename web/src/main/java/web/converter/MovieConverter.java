package web.converter;

import core.model.domain.Client;
import core.model.domain.Movie;
import org.springframework.stereotype.Component;
import web.dto.ClientDto;
import web.dto.MovieDto;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDto> {
    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        Movie movie = Movie.builder()
                .director(dto.getDirector())
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .mainStar(dto.getMainStar())
                .yearOfRelease(dto.getYearOfRelease())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder()
                .director(movie.getDirector())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .mainStar(movie.getMainStar())
                .yearOfRelease(movie.getYearOfRelease())
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}