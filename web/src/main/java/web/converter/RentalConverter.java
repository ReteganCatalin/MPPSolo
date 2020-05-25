package web.converter;

import core.model.domain.Client;
import core.model.domain.Movie;
import core.model.domain.Rental;
import org.springframework.stereotype.Component;
import web.dto.RentalDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class RentalConverter extends BaseConverter<Rental, RentalDto> {

    @PersistenceContext // or even @Autowired
    private EntityManager entityManager;

    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        Rental rental = Rental.builder()
                .client(entityManager.getReference(Client.class, dto.getClientID()))
                .movie(entityManager.getReference(Movie.class, dto.getMovieID()))
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
                .clientID(rental.getClient().getId())
                .movieID(rental.getMovie().getId())
                .day(rental.getDay())
                .month(rental.getMonth())
                .year(rental.getYear())
                .build();
        dto.setId(rental.getId());
        return dto;
    }
}
