package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Bookshelf;
import bookshelf.renewal.dto.record.ShelfNewSimpleDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookshelfDto {
    private Long id;
    private String member;
    private String bookshelfName;
    private List<ShelfNewSimpleDto> shelves = new ArrayList<>();
    private String bookshelfColor;
    private Long bookshelfBookCount;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String recentBookCover;


    public BookshelfDto(Long id, String member, String bookshelfName,
                        String bookshelfColor, Long bookshelfBookCount,
                        LocalDateTime createdDate, LocalDateTime lastModifiedDate, String recentBookCover) {
        this.id = id;
        this.member = member;
        this.bookshelfName = bookshelfName;
        this.bookshelfColor = bookshelfColor;
        this.bookshelfBookCount = bookshelfBookCount;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.recentBookCover = recentBookCover;
    }

    public BookshelfDto(Bookshelf bookshelf) {
        this.id = bookshelf.getId();
        this.member = bookshelf.getMember().getUsername();
        this.bookshelfName = bookshelf.getBookshelfName();
        this.bookshelfColor = bookshelf.getBookshelfColor();
        this.createdDate = bookshelf.getCreatedDate();
        this.lastModifiedDate = bookshelf.getLastModifiedDate();
    }



    public void addShelf(ShelfNewSimpleDto shelf) {
        this.shelves.add(shelf);
    }

}
