package web.controller;

import core.model.domain.Rental;
import core.service.RentalService;
import core.service.RentalServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.RentalConverter;
import web.dto.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RentalController {
    public static final Logger log= LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalServiceInterface rentalService;

    @Autowired
    private RentalConverter rentalConverter;


    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    List<RentalDto> getRentals() {
        //todo: log
        return rentalConverter
                .convertModelsToDtos(rentalService.getAllRentals().stream().collect(Collectors.toList()));

    }
    @CrossOrigin
    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    void saveRental(@RequestBody RentalDto RentalDto) {
        //todo log
        rentalService.addRental(rentalConverter.convertDtoToModel(RentalDto));
    }
    @CrossOrigin
    @RequestMapping(value = "/rentals", method = RequestMethod.PUT)
    RentalDto updateRental(@RequestBody RentalDto RentalDto) {
        //todo: log
        return rentalConverter.convertModelToDto( rentalService.updateRental(
                rentalConverter.convertDtoToModel(RentalDto)));
    }
    @CrossOrigin
    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable Long id){
        log.trace("Method deleteRental entered with id {}",id);

        rentalService.deleteRental(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value ="/sortRentals",method=RequestMethod.POST )
    List<RentalDto> getSortedRentals(@RequestBody SortDto sorted)
    {
        log.trace("Method getSortedRentals entered");
        List<String> directions=sorted.getDirections();
        List<String> columns=sorted.getColumns();
        Sort sort=null;
        if(directions.get(0).equals("Asc")) {
            sort = new Sort(columns.get(0)).ascending();
        }
        else
        {
            sort = new Sort(columns.get(0)).descending();
        }
        for(int index=1;index<directions.size();index++)
        {
            if(directions.get(index).equals("Asc")) {
                sort.and(new Sort(columns.get(index)).ascending());
            }
            else
            {
                sort.and(new Sort(columns.get(index)).descending());
            }
        }
        log.trace("Method getRentalsSorted sort {} created", sort);
        return rentalConverter.convertModelsToDtos(rentalService.getAllRentalsSorted(sort));
    }
    @CrossOrigin
    @RequestMapping(value = "/filterRentals/{year}", method=RequestMethod.GET)
    List<RentalDto> getFilteredRentals(@PathVariable Integer year)
    {
        log.trace("Method getFilteredRentals entered with Path Variable: year {}"+year);
        return rentalConverter
                .convertModelsToDtos(rentalService.filterRentalsByYear(year));
    }

    @RequestMapping(value= "/statRentals",method=RequestMethod.POST)
    List<RentalDto> getStatRentals(@RequestBody StatRentalDto properties)
    {
        log.trace("Method getStatRentals entered with properties={}",properties.toString());

        return rentalConverter
                .convertModelsToDtos(rentalService.statRentals(properties.getYearOfRelease(),properties.getClientLeastAge()));
    }
}