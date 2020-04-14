package Service;

import Model.domain.Client;

import Model.domain.Movie;
import Model.exceptions.MyException;
import Model.exceptions.ValidatorException;
import Model.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.*;
import org.springframework.data.domain.Sort;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
@Service
public class ClientService implements ClientServiceInterface{
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private ClientRepository repository;
    @Autowired
    private Validator<Client> validator;


    @Override
    public Optional<Client> FindOne(Long ID)
    {
        return this.repository.findById(ID);
    }
    /**
     * Calls the repository save method with a given Client Object
     *
     * @param client created client object to be passed over to the repository
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there exist already an entity with that ClientNumber
     */
    @Override
    @Transactional
    public void addClient(Client client) throws ValidatorException
    {
        log.trace("addClient - method entered: client={}", client);
        validator.validate(client);
        repository.findById(client.getId()).ifPresent(optional->{throw new MyException("Client already exists");});
        repository.save(client);
        log.debug("addClient - added: client={}", client);
        log.trace("addClient - method finished");
    }

    /**
     * Calls the repository update method with a certain Client Object
     *
     * @param client created movie object to be passed over to the repository
     * @return the updated object
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be updated.
     */
    @Transactional
    public Client updateClient(Client client) throws ValidatorException,MyException
    {
        validator.validate(client);
        log.trace("updateClient - method entered: client={}", client);
        Client updated=repository.findById(client.getId()).orElseThrow(()-> new MyException("No client to update"));
        repository.findById(client.getId())
                .ifPresent(c -> {
                    c.setFirstName(client.getFirstName());
                    c.setLastName(client.getLastName());
                    c.setAge(client.getAge());
                    log.debug("updateClient - updated: c={}", c);
                });
        System.out.println("here");
        log.trace("updateClient - method finished");
        return updated;
    }

    /**
     * Given the id of a client it calls the delete method of the repository with that id
     *
     * @param id the id of the client to be deleted
     * @return the deleted Client Instance
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws MyException
     *             if there is no entity to be deleted.
     */
    @Transactional
    public Client deleteClient(Long id) throws ValidatorException
    {
        log.trace("deleteClient - method entered: client={}", id);
        repository.findById(id)
                .orElseThrow(()->
                    new MyException("No client with that id")
                );
        Client deleted=repository.findById(id).get();
        repository.deleteById(id);
        log.debug("deleteClient - deleted: deleted{}", deleted);
        log.trace("deleteClient - method finished");
        return deleted;
    }

    /**
     * Gets all the Client Instances from the repository
     *
     * @return {@code Set} containing all the Clients Instances from the repository
     */
    public Set<Client> getAllClients()
    {
        log.trace("getAllClients - method entered");
        Iterable<Client> clients=repository.findAll();
        log.trace("getAllClients - method finished");
        return StreamSupport.stream(clients.spliterator(),false).collect(Collectors.toSet());

    }

    public List<Client> getAllClientsSorted(Sort sort)
    {
        log.trace("getAllClientsSorted - method entered sort={}",sort);
        Iterable<Client> sortedClients=repository.findAll(sort);
        log.trace("getAllClientsSorted - method finished");
        return StreamSupport.stream(sortedClients.spliterator(),false).collect(Collectors.toList());
    }

    /**
     * Filters all the clients by their First or Last Name
     *
     * @param name a substring of the First or Last Name of type {@code String}
     * @return {@code HashSet} containing all the Client Instances from the repository that contain the name parameter in the
     * first name or the last name
     */
    public Set<Client> filterClientsByName(String name)
    {
        log.trace("filterClientsByName - method entered name={}",name);
        Iterable<Client> clients=repository.findAll();
        Set<Client> filteredClients=new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client->!(client.getLastName().contains(name) || client.getFirstName().contains(name)) );
        log.trace("filterClientsByName - method finished");
        return filteredClients;
    }

    public List<Client> statOldestClients(){
        log.trace("statOldestClients - method entered ");
        log.trace("statOldestClients - method finished ");
        return StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList())
                .stream()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .collect(Collectors.toList());
    }

}
