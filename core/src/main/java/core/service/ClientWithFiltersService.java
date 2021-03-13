package core.service;

import core.model.domain.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ClientWithFiltersService")
public class ClientWithFiltersService extends ClientService{

    /**
     * Filters all the clients by their First or Last Name
     *
     * @param name a substring of the First or Last Name of type {@code String}
     * @return {@code HashSet} containing all the Client Instances from the repository that contain the name parameter in the
     * first name or the last name
     */
    public List<Client> filterClientsByName(String name) {
        log.trace("filterClientsByName - method entered name={}", name);
        List<Client> clients = repository.findClientsByName(name);
        log.trace("filterClientsByName - method finished filtered={}", clients);
        return clients;
    }

    @Override
    public List<Client> filterClientsByAge(Integer age) {
        log.trace("filterClientsByAge - method entered name={}", age);
        List<Client> clients = repository.findByAgeWithRentalAndMovie(age);
        //log.trace("filterClientsByAge - method finished filtered={}, rentals={}, movie={}",clients,clients.get(0).getRentals(),clients.get(0).getRentals().get(0).getMovie());
        log.trace("filterClientsByAge - method finished filtered={}", clients);
        return clients;
    }
}
