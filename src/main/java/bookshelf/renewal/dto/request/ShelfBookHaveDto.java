package bookshelf.renewal.dto.request;

import bookshelf.renewal.dto.BookDto;
import lombok.Data;

@Data
public class ShelfBookHaveDto {
    private Long shelfId;
    private BookDto bookDto;
}
