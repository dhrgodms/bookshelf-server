package bookshelf.renewal.dto.request;

import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.ShelfDto;
import lombok.Data;

@Data
public class ShelfBookRequestDto {
    private ShelfDto shelfDto;
    private BookDto bookDto;
}
