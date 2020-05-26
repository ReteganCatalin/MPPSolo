package core.service;

import core.model.domain.Client;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;
import core.model.validators.Validator;
import core.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService implements ClientServiceInterface {
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
    public Client updateClient(Client client) throws ValidatorException, MyException
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
        log.warn("getAllClientsSorted - method entered sort={}",sort);
        System.out.println(sort);
        Iterable<Client> sortedClients=repository.findAll(sort);
        log.trace("getAllClientsSorted - method finished ",sortedClients);
        return StreamSupport.stream(sortedClients.spliterator(),false).collect(Collectors.toList());
    }

    /**
     * Filters all the clients by their First or Last Name
     *
     * @param name a substring of the First or Last Name of type {@code String}
     * @return {@code HashSet} containing all the Client Instances from the repository that contain the name parameter in the
     * first name or the last name
     */
    public List<Client> filterClientsByName(String name)
    {
        log.trace("filterClientsByName - method entered name={}",name);
        List<Client> clients=repository.findClientsByName(name);
        log.trace("filterClientsByName - method finished filtered={}",clients);
        return clients;
    }
    @Override
    public List<Client> filterClientsByAge(Integer age)
    {
        log.trace("filterClientsByAge - method entered name={}",age);
        List<Client> clients=repository.findByAgeWithRentalAndMovie(age);
        //log.trace("filterClientsByAge - method finished filtered={}, rentals={}, movie={}",clients,clients.get(0).getRentals(),clients.get(0).getRentals().get(0).getMovie());
        log.trace("filterClientsByAge - method finished filtered={}",clients);
        return clients;
    }

    @Override
    public List<Client> filterClientsByFirstName(String firstName)
    {
        log.trace("filterClientsByFirstName - method entered name={}",firstName);
        List<Client> clients=repository.findByFirstName(firstName);
        log.trace("filterClientsByFirstName - method finished filtered={}",clients);
        return clients;
    }

    public List<Client> paginatedClients(Integer pageNo,Integer size)
    {
        log.trace("paginatedClients - method entered with pageNo={} and size={}",pageNo,size);
        PageRequest page=PageRequest.of(pageNo,size);
        Page<Client> clients=repository.findAll(page);

        log.trace("paginatedClients - method finished clients={}",clients);
        return clients.getContent();
    }


    public List<Client> statOldestClients(){
        log.trace("statOldestClients - method entered ");
        log.trace("statOldestClients - method finished ");
        return repository.findAll(new Sort("Age").descending());
    }

}
