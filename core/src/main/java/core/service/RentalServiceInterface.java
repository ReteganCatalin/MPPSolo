package core.service;

import core.model.domain.Rental;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;

import java.util.List;

public interface RentalServiceInterface {


    void addRental(Long ClientID,Long MovieID,int year,int month,int day) throws ValidatorException, MyException;
    Rental updateRental(Long ID,Long ClientID,Long MovieID,int year,int month,int day) throws ValidatorException, MyException;

    void deleteRental(Long id) throws ValidatorException;


    List<Rental> getAllRentals();

//    List<Rental> getAllRentalsSorted(Sort sort);
//
//    Set<Rental> filterRentalsByYear(int year);
//
//    List<Rental> paginatedRentals(Integer pageNo,Integer size);

//    Set<Rental> statRentals(int movie_year, int age);
}
