package bookshelf.renewal.dto;

import lombok.Data;

@Data
public class ShelfUpdateDto {
    private String username;
    private Long id;
    private String changedShelfName;
    private String changedShelfMemo;
}
