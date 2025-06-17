package bookshelf.renewal.dto;

import lombok.Data;

@Data
public class ShelfCreateDto {
    private String username;
    private ShelfDto shelfDto;
}
