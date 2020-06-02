package core.Repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.model.domain.Client;
import core.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-client.xml")
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void findAll() throws Exception {
        List<Client> clients = clientRepository.findAll();
        assertEquals("there should be four movies", 4, clients.size());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        Client client=Client.builder().age(21).lastName("Catalin").firstName("Rete").build();
        client.setId(1L);
        clientRepository.findById(client.getId())
                .ifPresent(c -> {
                    c.setFirstName(client.getFirstName());
                    c.setLastName(client.getLastName());
                    c.setAge(client.getAge());
                });
        List<Client> clients = clientRepository.findAll();
        assertEquals("The first name should be Rete", clients.get(0).getFirstName(),"Rete");
        assertEquals("there should be four clients", 4, clients.size());
    }

    @Test
    public void createClient() throws Exception {
        Client client=Client.builder().age(20).firstName("fN1").lastName("ln1").build();
        client.setId(-1L);
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();
        assertEquals("there should be five clients", 5, clients.size());
    }

    @Test
    public void deleteClient() throws Exception {
        clientRepository.deleteById(1L);
        List<Client> clients = clientRepository.findAll();
        assertEquals("there should be three clients", 3, clients.size());
    }
}
