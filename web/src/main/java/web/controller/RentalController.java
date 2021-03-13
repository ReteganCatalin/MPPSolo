package web.controller;

import core.model.domain.Rental;
import core.service.ClientServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.RentalConverter;
import web.dto.RentalDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RentalController {
    public static final Logger log= LoggerFactory.getLogger(RentalController.class);

    @Autowired
    @Qualifier("ClientService")
    private ClientServiceInterface clientService;

    @Autowired
    private RentalConverter rentalConverter;


    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    List<RentalDto> getRentals() {
        log.trace("Method getRentals entered");
        return rentalConverter
                .convertModelsToDtos(clientService.getAllRentals().stream().collect(Collectors.toList()));

    }
    @CrossOrigin
    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    void saveRental(@RequestBody RentalDto RentalDto) {
        log.trace("Method saveRental entered with rental ={}",RentalDto);
        Rental rental=rentalConverter.convertDtoToModel(RentalDto) ;
        log.trace(rental.toString());
        clientService.addRental(RentalDto.getClientID(),RentalDto.getMovieID(),RentalDto.getYear(),RentalDto.getMonth(),RentalDto.getDay());
    }
    @CrossOrigin
    @RequestMapping(value = "/rentals", method = RequestMethod.PUT)
    void updateRental(@RequestBody RentalDto RentalDto) {
        log.trace("Method updateRental entered with rental {}",RentalDto);
        clientService.updateRental(RentalDto.getId(),
                RentalDto.getClientID(),RentalDto.getMovieID(),RentalDto.getYear(),RentalDto.getMonth(),RentalDto.getDay());
        return;
    }
    @CrossOrigin
    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable Long id){
        log.trace("Method deleteRental entered with id {}",id);

        clientService.deleteRental(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @CrossOrigin
//    @RequestMapping(value ="/sortRentals",method=RequestMethod.POST )
//    List<RentalDto> getSortedRentals(@RequestBody SortDto sorted)
//    {
//        log.trace("Method getSortedRentals entered");
//        List<SortObjectDTO> sorts=sorted.getSort();
//        Sort sort=null;
//        if(sorts.get(0).getDirection().equals("Asc")) {
//            sort = new Sort(sorts.get(0).getColumn()).ascending();
//        }
//        else
//        {
//            sort = new Sort(sorts.get(0).getColumn()).descending();
//        }
//        for(int index=1;index<sorts.size();index++)
//        {
//            if(sorts.get(index).getDirection().equals("Asc")) {
//                sort=sort.and(new Sort(sorts.get(index).getColumn()).ascending());
//            }
//            else
//            {
//                sort=sort.and(new Sort(sorts.get(index).getColumn()).descending());
//            }
//        }
//        log.trace("Method getRentalsSorted sort {} created", sort);
//        return rentalConverter.convertModelsToDtos(rentalService.getAllRentalsSorted(sort));
//    }
//    @CrossOrigin
//    @RequestMapping(value = "/filterRentals/{year}", method=RequestMethod.GET)
//    List<RentalDto> getFilteredRentals(@PathVariable Integer year)
//    {
//        log.trace("Method getFilteredRentals entered with Path Variable: year {}"+year);
//        return rentalConverter
//                .convertModelsToDtos(rentalService.filterRentalsByYear(year));
//    }
//
//    @CrossOrigin
//    @RequestMapping(value = "/rentals/get-page/pageno={pageNo},size={size}",method=RequestMethod.GET)
//    List<RentalDto> getPaginatedRentals(@PathVariable Integer pageNo,@PathVariable Integer size)
//    {
//        log.trace("Method getPaginatedRentals entered with Path Variable: pageNo {} and size {}",pageNo,size);
//        return rentalConverter
//                .convertModelsToDtos(rentalService.paginatedRentals(pageNo,size));
//    }

//    @RequestMapping(value= "/statRentals",method=RequestMethod.POST)
//    List<RentalDto> getStatRentals(@RequestBody StatRentalDto properties)
//    {
//        log.trace("Method getStatRentals entered with properties={}",properties.toString());
//
//        return rentalConverter
//                .convertModelsToDtos(rentalService.statRentals(properties.getYearOfRelease(),properties.getClientLeastAge()));
//    }
}
