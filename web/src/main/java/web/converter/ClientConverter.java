package web.converter;

import core.model.domain.Client;
import org.springframework.stereotype.Component;
import web.converter.BaseConverter;
import web.dto.ClientDto;


@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = ClientDto.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .age(client.getAge())
                .build();
        dto.setId(client.getId());
        return dto;
    }
}