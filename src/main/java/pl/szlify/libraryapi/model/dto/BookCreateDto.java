package pl.szlify.libraryapi.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookCreateDto {

    @NotNull(message = "NULL_VALUE")
    @Size(min = 2, message = "VALUE_TOO_SMALL")
    private String title;

    @NotNull(message = "NULL_VALUE")
    @Size(min = 2, message = "VALUE_TOO_SMALL")
    private String author;

    @NotNull(message = "NULL_VALUE")
    @Size(min = 2, message = "VALUE_TOO_SMALL")
    private String category;
}
