package Service;

import Model.domain.Client;
import Model.domain.Movie;
import Model.domain.Rental;
import Model.exceptions.MyException;
import Model.exceptions.ValidatorException;
import repository.Sort;
import repository.SortingRepository;
import repository.Sort;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface RentalServiceInterface {


    void addRental(Rental rental) throws ValidatorException, MyException;
    Rental updateRental(Rental rental) throws ValidatorException, MyException;

    Rental deleteRental(Long id) throws ValidatorException;


    Set<Rental> getAllRentals();

    List<Rental> getAllRentalsSorted(Sort sort);

    Set<Rental> filterRentalsByYear(int year);

    Set<Rental> statMostRentedMovieReleasedThatYearRentalsByClientsAgedMoreThan(int movie_year, int age);
}