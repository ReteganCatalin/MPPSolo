package core.Service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.model.domain.Client;
import core.service.ClientServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-client.xml")
public class ClientServiceTest {

    @Autowired
    private ClientServiceInterface clientService;

    @Test
    public void findAll() throws Exception {
        Set<Client> clients = clientService.getAllClients();
        assertEquals("there should be four clients", 4, clients.size());
    }

    @Test
    public void updateClient() throws Exception {
        Client client=Client.builder().age(20).lastName("Catalin").firstName("alex").build();
        client.setId(1L);
        clientService.updateClient(client);
        Set<Client> clients = clientService.getAllClients();
        assertEquals("there should be four clients", 4, clients.size());
    }

    @Test
    public void createClient() throws Exception {
        Client client=Client.builder().age(20).lastName("Catalin").firstName("alex").build();
        client.setId(5L);
        clientService.addClient(client);
        Set<Client> clients = clientService.getAllClients();
        assertEquals("there should be five clients", 5, clients.size());
    }

    @Test
    public void deleteClient() throws Exception {
        clientService.deleteClient(1L);
        Set<Client> clients = clientService.getAllClients();
        assertEquals("there should be three clients", 3, clients.size());
    }

}
