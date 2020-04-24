package core.service;

import core.model.domain.Client;
import core.model.domain.Movie;
import core.model.domain.Rental;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;
import core.model.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentalService implements RentalServiceInterface {
    public static final Logger log = LoggerFactory.getLogger(RentalService.class);
    @Autowired
    private ClientServiceInterface clientServ;
    @Autowired
    private MovieServiceInterface movieServ;
    @Autowired
    private core.repository.RentalRepository RentalRepository;

    @Autowired
    private Validator<Rental> validator;

    private void checkIDs(Long ClientID, Long MovieID)
    {
        clientServ.FindOne(ClientID).orElseThrow(()->new MyException("Client ID not found! "));
        movieServ.FindOne(MovieID).orElseThrow(()->new MyException("Movie ID not found! "));
    }
    /**
     * Calls the repository save method with a given Rental Object
     *
     * @param rental created rental object to be passed over to the repository
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there exist already an entity with that ClientNumber
     */
    public void addRental(Rental rental) throws ValidatorException, MyException
    {
        log.trace("addRental - method entered: rental={}", rental);
        checkIDs(rental.getClientID(),rental.getMovieID());
        Iterable<Rental> rentals=RentalRepository.findAll();
        Set<Rental> filteredRentals=StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
        filteredRentals
                .stream()
                .filter(exists-> (exists.getClientID()==rental.getClientID()) && exists.getMovieID()==rental.getMovieID())
                .findFirst().ifPresent(optional->{throw new MyException("Rental for that movie and client exists");});

        validator.validate(rental);
        RentalRepository.findById(rental.getId()).ifPresent(optional->{throw new MyException("Rental already exists");});
        RentalRepository.save(rental);
        log.debug("addRental - added: added={}", rental);
        log.trace("addRental - method finished");
    }

    /**
     * Calls the repository update method with a certain Client Object
     *
     * @param rental created rental object to be passed over to the repository
     * @return the updated object
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be updated.
     */
    @Transactional
    public Rental updateRental(Rental rental) throws ValidatorException, MyException
    {
        log.trace("updateRental - method entered: rental={}", rental);
        Optional<Rental> found_rental=RentalRepository.findById(rental.getId());
        found_rental.orElseThrow(()-> new MyException("No Rental to update"));
        Long ClientID=found_rental.get().getClientID();
        Long MovieID=found_rental.get().getMovieID();
        rental.setClientID(ClientID);
        rental.setMovieID(MovieID);
        validator.validate(rental);
        RentalRepository.findById(rental.getId())
                .ifPresent(r -> {
                    r.setDay(rental.getDay());
                    r.setMonth(rental.getMonth());
                    r.setYear(rental.getYear());
                    log.debug("updateRental - updated: c={}", r);
                });
        log.trace("updateRental - method finished");
        return found_rental.get();
    }

    /**
     * Given the id of a rental it calls the delete method of the repository with that id
     *
     * @param id the id of the rental to be deleted
     * @return the deleted Client Instance
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be deleted.
     */
    public Rental deleteRental(Long id) throws ValidatorException
    {
        log.trace("deleteRental - method entered: id={}", id);
        RentalRepository.findById(id)
                .orElseThrow(()->
                        new MyException("No rental with that id")
                );
        Rental deleted=RentalRepository.findById(id).get();
        RentalRepository.deleteById(id);
        log.debug("deleteRental - delete: deleted={}", deleted);
        log.trace("deleteRental - method finished");
        return deleted;
    }

    /**
     * Gets all the Rentals Instances from the repository
     *
     * @return {@code Set} containing all the Clients Instances from the repository
     */



    public Set<Rental> getAllRentals()
    {
        log.trace("getAllRentals - method entered");
        Iterable<Rental> rentals=RentalRepository.findAll();
        log.trace("getAllRentals - method finished");
        return StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());

    }

    public List<Rental> getAllRentalsSorted(Sort sort)
    {
        log.trace("getAllRentalsSorted - method entered sort={}",sort);
        Iterable<Rental> sortedRentals=RentalRepository.findAll(sort);
        log.trace("getAllRentalsSorted - method finished");
        return StreamSupport.stream(sortedRentals.spliterator(),false).collect(Collectors.toList());

    }

    /**
     * Filters all the rentals by their Years
     *
     * @param year a substring of the year of type {@code String}
     * @return {@code HashSet} containing all the Rental Instances from the repository that have been rented in that year
     *
     */
    public Set<Rental> filterRentalsByYear(int year)
    {
        log.trace("filterRentalsByYear - method entered year={}",year);
        Iterable<Rental> rentals= RentalRepository.findAll();
        Set<Rental> filteredRentals=new HashSet<>();
        rentals.forEach(filteredRentals::add);
        filteredRentals.removeIf(rental->!(rental.getYear()==year) );
        log.trace("filterRentalsByYear - method finished");
        return filteredRentals;
    }

    public Set<Rental> statMostRentedMovieReleasedThatYearRentalsByClientsAgedMoreThan(int movie_year, int age){
        log.trace("statMostRentedMovieReleasedThatYearRentalsByClientsAgedMoreThan - method entered movie_year={},age={}",movie_year,age);
        List<Client> ClientList=clientServ.getAllClients().stream().filter(client->client.getAge()>=age).collect(Collectors.toList());
        List<Movie> MovieList=movieServ.getAllMovies().stream().filter(movie->movie.getYearOfRelease()==movie_year).collect(Collectors.toList());
        List<Rental> rentalsList = StreamSupport.stream(RentalRepository.findAll().spliterator(),false)
                .filter(rental->ClientList.stream().filter(client->client.getId().equals(rental.getClientID())).collect(Collectors.toList()).size()>0)
                .filter(rental->MovieList.stream().filter(movie->movie.getId().equals(rental.getMovieID())).collect(Collectors.toList()).size()>0)
                .collect(Collectors.toList());

        Long mostRentedMovie = Collections.max(rentalsList.stream()
                .map(Rental::getMovieID)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                ,
                Comparator.comparingLong(Map.Entry::getValue))
                .getKey();

        Set<Rental> filteredRentals = rentalsList.stream()
                .filter(rental -> rental.getMovieID().equals(mostRentedMovie))
                .collect(Collectors.toSet());
        log.trace("statMostRentedMovieReleasedThatYearRentalsByClientsAgedMoreThan - method finished");

        return filteredRentals;
    }
}
