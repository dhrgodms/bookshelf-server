package bookshelf.renewal.dto.request;

import bookshelf.renewal.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookKeepRequestDto {
    private BookDto bookDto;
    private String username;
    private List<String> location;
}
