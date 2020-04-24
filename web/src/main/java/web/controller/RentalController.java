package web.controller;

import core.service.RentalService;
import core.service.RentalServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.RentalConverter;
import web.dto.RentalDto;
import web.dto.RentalsDto;

import java.util.stream.Collectors;

@RestController
public class RentalController {
    public static final Logger log= LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalServiceInterface rentalService;

    @Autowired
    private RentalConverter rentalConverter;


    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    RentalsDto getRentals() {
        //todo: log
        return new RentalsDto(rentalConverter
                .convertModelsToDtos(rentalService.getAllRentals().stream().collect(Collectors.toList())));

    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    void saveRental(@RequestBody RentalDto RentalDto) {
        //todo log
        rentalService.addRental(rentalConverter.convertDtoToModel(RentalDto));
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.PUT)
    RentalDto updateRental(@RequestBody RentalDto RentalDto) {
        //todo: log
        return rentalConverter.convertModelToDto( rentalService.updateRental(
                rentalConverter.convertDtoToModel(RentalDto)));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@PathVariable Long id){
        //todo:log

        rentalService.deleteRental(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}