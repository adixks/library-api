package pl.szlify.libraryapi.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SubscriptionDto {

    private Long id;
    private Long clientId;
    private String category;
    private String author;
}
