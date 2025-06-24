package bookshelf.renewal.dto.request;

import bookshelf.renewal.dto.ShelfDto;
import lombok.Data;

@Data
public class ShelfCreateDto {
    private String username;
    private ShelfDto shelfDto;
}
