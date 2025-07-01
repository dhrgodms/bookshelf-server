package bookshelf.renewal.dto;

import bookshelf.renewal.domain.ShelfNew;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShelfNewDto {
    private Long id;
    private Long bookshelfId;
    private Integer shelfPosition;
    private String shelfCustomName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @QueryProjection
    public ShelfNewDto(Long bookshelfId, Integer shelfPosition, String shelfCustomName, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.bookshelfId = bookshelfId;
        this.shelfPosition = shelfPosition;
        this.shelfCustomName = shelfCustomName;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public ShelfNewDto(ShelfNew shelfNew) {
        this.id = shelfNew.getId();
        this.bookshelfId = shelfNew.getBookshelf().getId();
        this.shelfPosition = shelfNew.getShelfPosition();
        this.shelfCustomName = shelfNew.getShelfCustomName();
        this.createdDate = shelfNew.getCreatedDate();
        this.lastModifiedDate = shelfNew.getLastModifiedDate();
    }
}