package bookshelf.renewal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookSaveRequestDto {
    private BookDto bookDto;
    private String username;


}
