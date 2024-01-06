package pl.szlify.libraryapi.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClientCreateDto {

    @NotNull(message = "NULL_VALUE")
    @Size(min = 2, message = "VALUE_TOO_SMALL")
    private String firstName;

    @NotNull(message = "NULL_VALUE")
    @Size(min = 2, message = "VALUE_TOO_SMALL")
    private String lastName;

    @Email
    @NotNull(message = "NULL_VALUE")
    private String email;
}
