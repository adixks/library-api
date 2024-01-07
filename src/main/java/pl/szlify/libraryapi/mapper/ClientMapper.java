package pl.szlify.libraryapi.mapper;

import org.springframework.stereotype.Component;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.dto.ClientCreateDto;
import pl.szlify.libraryapi.model.dto.ClientDto;

@Component
public class ClientMapper {

    public ClientEntity toEntity(ClientCreateDto clientCreateDto) {
        return new ClientEntity()
                .setFirstName(clientCreateDto.getFirstName())
                .setLastName(clientCreateDto.getLastName())
                .setEmail(clientCreateDto.getEmail());
    }

    public ClientDto toDto(ClientEntity clientEntity) {
        return new ClientDto()
                .setId(clientEntity.getId())
                .setFirstName(clientEntity.getFirstName())
                .setLastName(clientEntity.getLastName())
                .setEmail(clientEntity.getEmail());
    }
}
