package bookshelf.renewal.dto.request;

import bookshelf.renewal.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookKeepRequestDto {
    private BookDto bookDto;
    private String username;
    private Long shelfId;
    private Long bookshelfId;
}
