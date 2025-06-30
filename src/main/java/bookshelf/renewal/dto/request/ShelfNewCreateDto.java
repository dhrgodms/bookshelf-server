package bookshelf.renewal.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShelfNewCreateDto {
    private Long bookshelfId;
    private Integer shelfPosition;
    private String shelfCustomName;
    private String shelfColor;
    private String username;

    public ShelfNewCreateDto(Long bookshelfId, Integer shelfPosition, String shelfCustomName, String shelfColor, String username) {
        this.bookshelfId = bookshelfId;
        this.shelfPosition = shelfPosition;
        this.shelfCustomName = shelfCustomName;
        this.shelfColor = shelfColor;
        this.username = username;
    }

    public ShelfNewCreateDto(Long bookshelfId, Integer shelfPosition, String username) {
        this.bookshelfId = bookshelfId;
        this.shelfPosition = shelfPosition;
        this.username = username;
    }
}
