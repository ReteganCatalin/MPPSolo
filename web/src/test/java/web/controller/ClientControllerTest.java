package web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.model.domain.Client;
import core.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web.converter.ClientConverter;
import web.dto.ClientDto;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




public class ClientControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientConverter clientConverter;

    private Client client1;
    private Client client2;
    private ClientDto clientDto1;
    private ClientDto clientDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .build();
        initData();
    }

    @Test
    public void getClients() throws Exception {
        List<Client> clients = Arrays.asList(client1, client2);

        List<ClientDto> clientDtos =
                new ArrayList<>(Arrays.asList(clientDto1, clientDto2));
        when(clientService.getAllClients()).thenReturn(clients);
        when(clientConverter.convertModelsToDtos(clients)).thenReturn(clientDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", anyOf(is("c1"), is("c2"))))
                .andExpect(jsonPath("$[1].age", anyOf(is(22), is(21))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println(result);

        verify(clientService, times(1)).getAllClients();
        verify(clientConverter, times(1)).convertModelsToDtos(clients);
        verifyNoMoreInteractions(clientService, clientConverter);


    }

    @Test
    public void updateClient() throws Exception {

        when(clientService.updateClient(client1))
                .thenReturn(client1);

        when(clientConverter.convertModelToDto(any(Client.class))).thenReturn(clientDto1);
        when(clientConverter.convertDtoToModel(any(ClientDto.class))).thenReturn(client1);

        Map<String, ClientDto> clientDtoMap = new HashMap<>();
        clientDtoMap.put("client", clientDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/clients", client1.getId(), clientDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(clientDto1)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", is("c1")));

        verify(clientService, times(1)).updateClient(client1);
        verify(clientConverter, times(1)).convertModelToDto(any(Client.class));
        verify(clientConverter, times(1)).convertDtoToModel(any(ClientDto.class));
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    private String toJsonString(Map<String, ClientDto> clientDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(clientDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString(ClientDto clientDto) {
        try {
            return new ObjectMapper().writeValueAsString(clientDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createClient() throws Exception {
        when(clientConverter.convertDtoToModel(any(ClientDto.class))).thenReturn(client1);
        ResultActions resultActions =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders
                                        .post("/clients", client1)
                                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                                        .content(toJsonString(clientDto1)))
                        .andExpect(status().isOk());
        verify(clientService, times(1)).addClient(client1);
        verify(clientConverter, times(1)).convertDtoToModel(any(ClientDto.class));
        verifyNoMoreInteractions(clientService,clientConverter);

    }

    @Test
    public void deleteClient() throws Exception {


        ResultActions resultActions =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders
                                        .delete("/clients/{Id}", client1.getId())
                                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk());


        verify(clientService, times(1)).deleteClient(client1.getId());
        verifyNoMoreInteractions(clientService,clientConverter);

    }

    private void initData() {
        client1 = Client.builder().age(21).firstName("c1").lastName("cL1").build();
        client1.setId(1l);
        client2 = Client.builder().age(22).firstName("c1").lastName("cL1").build();
        client2.setId(2l);

        clientDto1 = createClientDto(client1);
        clientDto2 = createClientDto(client2);

    }

    private ClientDto createClientDto(Client client) {
        ClientDto clientDto = ClientDto.builder()
                .age(client.getAge())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .build();
        clientDto.setId(client.getId());
        return clientDto;
    }


}
