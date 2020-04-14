package Service;

import Model.domain.Rental;
import Model.exceptions.MyException;
import Model.exceptions.ValidatorException;
import org.springframework.data.domain.Sort;


import java.util.*;

public interface RentalServiceInterface {


    void addRental(Rental rental) throws ValidatorException, MyException;
    Rental updateRental(Rental rental) throws ValidatorException, MyException;

    Rental deleteRental(Long id) throws ValidatorException;


    Set<Rental> getAllRentals();

    List<Rental> getAllRentalsSorted(Sort sort);

    Set<Rental> filterRentalsByYear(int year);

    Set<Rental> statMostRentedMovieReleasedThatYearRentalsByClientsAgedMoreThan(int movie_year, int age);
}