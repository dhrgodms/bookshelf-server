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

    public ShelfNewCreateDto(Long bookshelfId, Integer shelfPosition, String shelfCustomName, String shelfColor) {
        this.bookshelfId = bookshelfId;
        this.shelfPosition = shelfPosition;
        this.shelfCustomName = shelfCustomName;
        this.shelfColor = shelfColor;
    }

    public ShelfNewCreateDto(Long bookshelfId, Integer shelfPosition) {
        this.bookshelfId = bookshelfId;
        this.shelfPosition = shelfPosition;
    }
}
