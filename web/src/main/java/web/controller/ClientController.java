package web.controller;

import core.model.domain.Client;
import core.service.ClientService;
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
import web.dto.ClientsDto;
import web.dto.SortDto;
import web.dto.StudentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientController {
    public static final Logger log= LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientServiceInterface clientService;

    @Autowired
    private ClientConverter clientConverter;


    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientsDto getClients() {
        log.trace("Method getClients entered");
        return new ClientsDto(clientConverter
                .convertModelsToDtos(new ArrayList<>(clientService.getAllClients())));

    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    void saveClient(@RequestBody ClientDto clientDto) {
        log.trace("Method saveClients entered with ClientDto {}",clientDto);
        clientService.addClient(clientConverter.convertDtoToModel(clientDto));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.PUT)
    ClientDto updateClient(@RequestBody ClientDto clientDto) {
        log.trace("Method updateClients entered with ClientDto {}",clientDto);
        return clientConverter.convertModelToDto( clientService.updateClient(
                clientConverter.convertDtoToModel(clientDto)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@PathVariable Long id){
        log.trace("Method deleteClients entered with id {}",id);
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value ="/sortClients",method=RequestMethod.POST )
    ClientsDto getSortedClients(@RequestBody SortDto sorted)
    {
        log.trace("Method getSortedClients entered");
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
        log.trace("Method getClientsSorted sort {} created", sort);
        return new ClientsDto(clientConverter.convertModelsToDtos(clientService.getAllClientsSorted(sort)));
    }

    @RequestMapping(value = "/filterClients/{name}", method=RequestMethod.GET)
    ClientsDto getFilteredClients(@PathVariable String name)
    {
        log.trace("Method getFilteredClients entered with Path Variable: name {}"+name);
        return new ClientsDto(clientConverter
                .convertModelsToDtos(clientService.filterClientsByName(name)));
    }

    @RequestMapping(value= "/statClients",method=RequestMethod.GET)
    ClientsDto getStatClients()
    {
        log.trace("Method getStatClients entered");
        return new ClientsDto(clientConverter
                .convertModelsToDtos(clientService.statOldestClients()));
    }
}