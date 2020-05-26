package core.service;

import core.model.domain.Client;
import core.model.domain.Rental;
import core.model.exceptions.MyException;
import core.model.exceptions.ValidatorException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClientServiceInterface {

    public Optional<Client> FindOne(Long ID) ;
    public void addClient(Client client) throws ValidatorException;
    public Client updateClient(Client client) throws ValidatorException, MyException;
    public Client deleteClient(Long id) throws ValidatorException;
    public Set<Client> getAllClients() ;

    public List<Client> getAllClientsSorted(Sort sort) ;
    List<Client> paginatedClients(Integer pageNo,Integer size);

    List<Client> filterClientsByName(String name);
    List<Client> filterClientsByFirstName(String name);
    List<Client> filterClientsByAge(Integer age);

    List<Client> statOldestClients();

    void addRental(Long ClientID,Long MovieID,int year,int month,int day) throws ValidatorException, MyException;
    Rental updateRental(Long ID, Long ClientID, Long MovieID, int year, int month, int day) throws ValidatorException, MyException;

    void deleteRental(Long id) throws ValidatorException;


    List<Rental> getAllRentals();
}
