package Service;

import Model.domain.Client;
import Model.exceptions.MyException;
import Model.exceptions.ValidatorException;
import repository.Sort;
import repository.SortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface ClientServiceInterface {

    public Optional<Client> FindOne(Long ID) ;
    public void addClient(Client client) throws ValidatorException ;
    public Client updateClient(Client client) throws ValidatorException, MyException;
    public Client deleteClient(Long id) throws ValidatorException ;
    public Set<Client> getAllClients() ;

    public List<Client> getAllClientsSorted(Sort sort) ;


    Set<Client> filterClientsByName(String name);

    List<Client> statOldestClients();
}