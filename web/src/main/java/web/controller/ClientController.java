package web.controller;

import core.service.ClientServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.ClientConverter;
import web.dto.ClientDto;
import web.dto.SortDto;
import web.dto.SortObjectDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {
    public static final Logger log= LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientServiceInterface clientService;

    @Autowired
    private ClientConverter clientConverter;


    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    List<ClientDto> getClients() {
        log.trace("Method getClients entered");
        return clientConverter
                .convertModelsToDtos(new ArrayList<>(clientService.getAllClients()));

    }
    @CrossOrigin
    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    void saveClient(@RequestBody ClientDto clientDto) {
        log.trace("Method saveClient entered with ClientDto {}",clientDto);
        clientService.addClient(clientConverter.convertDtoToModel(clientDto));
    }
    @CrossOrigin
    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id,@RequestBody ClientDto clientDto) {
        log.trace("Method updateClient entered with ClientDto {}",clientDto);
        return clientConverter.convertModelToDto( clientService.updateClient(
                clientConverter.convertDtoToModel(clientDto)));
    }
    @CrossOrigin
    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        log.trace("Method deleteClient entered with id {}",id);
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value ="/sortClients",method=RequestMethod.POST )
    List<ClientDto> getSortedClients(@RequestBody SortDto sorted)
    {
        log.trace("Method getSortedClients entered {} ", sorted);
        List<SortObjectDTO> sorts=sorted.getSort();
        log.trace("Method getSortedClients entered {} ", sorts.size());
        Sort sort=null;
        if(sorts.get(0).getDirection().equals("Asc")) {
            sort = new Sort(sorts.get(0).getColumn()).ascending();
        }
        else
        {
            sort = new Sort(sorts.get(0).getColumn()).descending();
        }
        for(int index=1;index<sorts.size();index++)
        {
            log.trace("Method getClientsSorted sort {} created", sort);
            if(sorts.get(index).getDirection().equals("Asc")) {
                sort=sort.and(new Sort(sorts.get(index).getColumn()).ascending());
            }
            else
            {
                sort=sort.and(new Sort(sorts.get(index).getColumn()).descending());
            }
        }
        log.trace("Method getClientsSorted sort {} created", sort);
        return clientConverter.convertModelsToDtos(clientService.getAllClientsSorted(sort));
    }
    @CrossOrigin
    @RequestMapping(value = "/filterClients/{name}", method=RequestMethod.GET)
    List<ClientDto> getFilteredClients(@PathVariable String name)
    {
        log.trace("Method getFilteredClients entered with Path Variable: name {}"+name);
        return clientConverter
                .convertModelsToDtos(clientService.filterClientsByName(name));
    }

    @CrossOrigin
    @RequestMapping(value = "/clients/get-page/pageno={pageNo},size={size}",method=RequestMethod.GET)
    List<ClientDto> getPaginatedClients(@PathVariable Integer pageNo,@PathVariable Integer size)
    {
        log.trace("Method getPaginatedClients entered with Path Variable: pageNo {} and size {}",pageNo,size);
        return clientConverter
                .convertModelsToDtos(clientService.paginatedClients(pageNo,size));
    }

    @RequestMapping(value= "/statClients",method=RequestMethod.GET)
    List<ClientDto> getStatClients()
    {
        log.trace("Method getStatClients entered");
        return clientConverter
                .convertModelsToDtos(clientService.statOldestClients());
    }
}
