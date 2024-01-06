package pl.szlify.libraryapi.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import pl.szlify.libraryapi.validator.CategoryOrAuthor;

@Data
@Accessors(chain = true)
@CategoryOrAuthor
public class SubscriptionCreateDto {

    @NotNull(message = "NULL_VALUE")
    private Long clientId;

    private String category;
    private String author;
}
