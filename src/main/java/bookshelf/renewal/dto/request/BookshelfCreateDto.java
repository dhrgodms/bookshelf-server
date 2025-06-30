package bookshelf.renewal.dto.request;

import lombok.Data;

@Data
public class BookshelfCreateDto {
    private String username;
    private String bookshelfName;
    private String bookshelfColor;
    private String notes;
}
