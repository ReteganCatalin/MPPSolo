package core.service;

import core.model.domain.Client;
import core.model.domain.Movie;
import core.model.domain.Rental;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;
import core.model.validators.Validator;
import core.repository.ClientRepository;
import core.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class RentalService implements RentalServiceInterface {
    public static final Logger log = LoggerFactory.getLogger(RentalService.class);
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private Validator<Rental> validator;

    @Autowired
    private EntityManager entityManager;

    private void checkIDs(Long ClientID, Long MovieID)
    {
        log.trace("checkIDs - method entered: clientID={} , movieID={}", ClientID,MovieID);
        clientRepository.findAllWithRentals().stream().filter(entry->entry.getId().equals(ClientID)).findFirst().orElseThrow(()->new MyException("Client ID not found! "));
        movieRepository.findAllWithRentals().stream().filter(entry->entry.getId().equals(MovieID)).findFirst().orElseThrow(()->new MyException("Movie ID not found! "));
    }
    /**
     * Calls the repository save method with a given Rental Object
     *
     * @param ClientID created rental object to be passed over to the repository
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there exist already an entity with that ClientNumber
     */
    @Transactional
    public void addRental(Long ClientID,Long MovieID,int year,int month,int day) throws ValidatorException, MyException
    {
        Rental rental= Rental.builder()
                .day(day)
                .month(month)
                .year(year)
                .client(entityManager.getReference(Client.class,ClientID))
                .movie(entityManager.getReference(Movie.class,MovieID))
                .build();
        log.trace("addRental - method entered: rental={}", rental);
        checkIDs(rental.getClient().getId(),rental.getMovie().getId());
        List<Movie> movies=movieRepository.findAllWithRentals();
        List<Rental>  rentals=movies.stream().map(entry->entry.getRentals()).reduce(new ArrayList<>(),(a,b)->{a.addAll(b);return a;});
        Set<Rental> filteredRentals=StreamSupport.stream(rentals.spliterator(),false).collect(Collectors.toSet());
        filteredRentals
                .stream()
                .filter(exists-> (exists.getClient().getId().equals(rental.getClient().getId())) && exists.getMovie().getId().equals(rental.getMovie().getId()))
                .findFirst().ifPresent(optional->{throw new MyException("Rental for that movie and client exists");});

        validator.validate(rental);
        if(rentals.contains(rental)==true) {
            throw new MyException("Rental already exists");
        }
        clientRepository.findAllWithRentals()
                .stream()
                .filter(entry->entry.equals(rental.getClient()))
                .findFirst()
                .get()
                .getRentals()
                .add(rental);
        movieRepository.findAllWithRentals().stream().filter(entry->entry.equals(rental.getMovie())).findFirst().get().getRentals().add(rental);

        log.debug("addRental - added: added={}", rental);
        log.trace("addRental - method finished");
    }

    /**
     * Calls the repository update method with a certain Client Object
     *
     * @param ID created rental object to be passed over to the repository
     * @return the updated object
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be updated.
     */
    @Transactional
    public Rental updateRental(Long ID,Long ClientID,Long MovieID,int year,int month,int day) throws ValidatorException, MyException
    {
        //Maybe have to do for movies too ?
        Rental rental= Rental.builder()
                .day(day)
                .month(month)
                .year(year)
                .client(entityManager.getReference(Client.class,ClientID))
                .movie(entityManager.getReference(Movie.class,MovieID))
                .build();
        rental.setId(ID);
        log.trace("updateRental - method entered: rental={}", rental);
        Optional<Client> found_client=clientRepository.findById(rental.getClient().getId());
        Optional<Rental> found_rental= found_client.get().getRentals().stream().filter(entry->entry.getId().equals(rental.getId())).findFirst();
        found_rental.orElseThrow(()-> new MyException("No Rental to update"));
        validator.validate(rental);
        clientRepository
                .findAllWithRentals().stream().filter(entry->entry.getId().equals(ClientID))
                .findFirst()
                .get()
                .getRentals()
                .stream().filter(entry->entry.getId().equals(ID))
                .findFirst()
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
    @Transactional
    public void deleteRental(Long id) throws ValidatorException
    {
        log.trace("deleteRental - method entered: id={}", id);
        Optional<Rental> rentalOptional =
                movieRepository.findAllWithRentals().stream()
                        .map(elem -> elem.getRentals())
                        .flatMap(elem -> elem.stream())
                        .filter(elem -> elem.getId().equals(id))
                        .findFirst();
        rentalOptional.ifPresent(
                elem -> {
                    Optional<Client> client =
                            clientRepository.findByIDWithRentals(elem.getClient().getId());
                    client.ifPresent(cli -> cli.getRentals().remove(elem));
                });
        rentalOptional.ifPresent(elem -> {
            Optional<Movie> movie =
                    movieRepository.findByIDWithRentals(elem.getMovie().getId());
            movie.ifPresent(mov -> mov.getRentals().remove(elem));
        });
        log.trace("deleteRental - method finished");
        return;
    }

    /**
     * Gets all the Rentals Instances from the repository
     *
     * @return {@code Set} containing all the Clients Instances from the repository
     */



    public List<Rental> getAllRentals()
    {
        log.trace("getAllRentals - method entered");
        List<Client> clients=clientRepository.findAllWithRentals();
        List<Rental>  rentals=clients.stream().map(entry->entry.getRentals()).reduce(new ArrayList<>(),(a,b)->{a.addAll(b);return a;});
        log.trace("getAllRentals - method finished");
        return rentals;

    }

//    public List<Rental> getAllRentalsSorted(Sort sort)
//    {
//        log.trace("getAllRentalsSorted - method entered sort={}",sort);
//        Iterable<Rental> sortedRentals=RentalRepository.findAll(sort);
//        log.trace("getAllRentalsSorted - method finished");
//        return StreamSupport.stream(sortedRentals.spliterator(),false).collect(Collectors.toList());
//
//    }
//
//    /**
//     * Filters all the rentals by their Years
//     *
//     * @param year a substring of the year of type {@code String}
//     * @return {@code HashSet} containing all the Rental Instances from the repository that have been rented in that year
//     *
//     */
//    public Set<Rental> filterRentalsByYear(int year)
//    {
//        log.trace("filterRentalsByYear - method entered year={}",year);
//        Iterable<Rental> rentals= RentalRepository.findAll();
//        Set<Rental> filteredRentals=new HashSet<>();
//        rentals.forEach(filteredRentals::add);
//        filteredRentals.removeIf(rental->!(rental.getYear()==year) );
//        log.trace("filterRentalsByYear - method finished");
//        return filteredRentals;
//    }
//
//    public List<Rental> paginatedRentals(Integer pageNo,Integer size)
//    {
//        log.trace("paginatedRentals - method entered with pageNo={} and size={}",pageNo,size);
//        PageRequest page=PageRequest.of(pageNo,size);
//        Page<Rental> rentals=RentalRepository.findAll(page);
//
//        log.trace("paginatedRentals - method finished clients={}",rentals);
//        return rentals.getContent();
//    }

//    public Set<Rental> statRentals(int movie_year, int age){
//        log.trace("statRentals - method entered movie_year={},age={}",movie_year,age);
//        List<Client> ClientList=clientServ.getAllClients().stream().filter(client->client.getAge()>=age).collect(Collectors.toList());
//        List<Movie> MovieList=movieServ.getAllMovies().stream().filter(movie->movie.getYearOfRelease()==movie_year).collect(Collectors.toList());
//        List<Rental> rentalsList = StreamSupport.stream(RentalRepository.findAll().spliterator(),false)
//                .filter(rental->ClientList.stream().filter(client->client.getId().equals(rental.getClient().getId())).collect(Collectors.toList()).size()>0)
//                .filter(rental->MovieList.stream().filter(movie->movie.getId().equals(rental.getMovie().getId())).collect(Collectors.toList()).size()>0)
//                .collect(Collectors.toList());
//
//        Long mostRentedMovie = Collections.max(rentalsList.stream()
//                .map(Rental::getMovie)
//                .map(entry->entry.getId())
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
//                .entrySet()
//                ,
//                Comparator.comparingLong(Map.Entry::getValue))
//                .getKey();
//
//        Set<Rental> filteredRentals = rentalsList.stream()
//                .filter(rental -> rental.getMovie().getId().equals(mostRentedMovie))
//                .collect(Collectors.toSet());
//        log.trace("statRentals - method finished");
//
//        return filteredRentals;
//    }
}
