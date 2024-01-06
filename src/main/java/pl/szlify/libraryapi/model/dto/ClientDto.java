package pl.szlify.libraryapi.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
