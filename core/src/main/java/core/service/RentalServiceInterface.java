package core.service;

import core.model.domain.Rental;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

public interface RentalServiceInterface {


    void addRental(Rental rental) throws ValidatorException, MyException;
    Rental updateRental(Rental rental) throws ValidatorException, MyException;

    Rental deleteRental(Long id) throws ValidatorException;


    Set<Rental> getAllRentals();

    List<Rental> getAllRentalsSorted(Sort sort);

    Set<Rental> filterRentalsByYear(int year);

    Set<Rental> statMostRentedMovieReleasedThatYearRentalsByClientsAgedMoreThan(int movie_year, int age);
}