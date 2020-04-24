package web.converter;

import core.model.domain.Movie;
import core.model.domain.Rental;
import org.springframework.stereotype.Component;
import web.dto.MovieDto;
import web.dto.RentalDto;

@Component
public class RentalConverter extends BaseConverter<Rental, RentalDto> {
    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        Rental rental = Rental.builder()
                .ClientID(dto.getClientID())
                .MovieID(dto.getMovieID())
                .day(dto.getDay())
                .month(dto.getMonth())
                .year(dto.getYear())
                .build();
        rental.setId(dto.getId());
        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        RentalDto dto = RentalDto.builder()
                .ClientID(rental.getClientID())
                .MovieID(rental.getMovieID())
                .day(rental.getDay())
                .month(rental.getMonth())
                .year(rental.getYear())
                .build();
        dto.setId(rental.getId());
        return dto;
    }
}