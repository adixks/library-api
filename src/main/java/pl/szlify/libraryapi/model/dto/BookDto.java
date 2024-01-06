package pl.szlify.libraryapi.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private String category;
}
