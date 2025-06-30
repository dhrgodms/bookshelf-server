package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Bookshelf;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.ShelfNew;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookshelfDto {
    private Long id;
    private String member;
    private String bookshelfName;
    private List<ShelfNewDto> shelves = new ArrayList<>();
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @QueryProjection
    public BookshelfDto(Long id, Member member, String bookshelfName, List<ShelfNew> shelves, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.member = member.getUsername();
        this.bookshelfName = bookshelfName;
        this.shelves = shelves.stream().map(shelfNew -> new ShelfNewDto(shelfNew)).toList();
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public BookshelfDto(Bookshelf bookshelf) {
        this.id = bookshelf.getId();
        this.member = bookshelf.getMember().getUsername();
        this.bookshelfName = bookshelf.getBookshelfName();
        this.shelves = bookshelf.getShelves().stream().map(shelfNew -> new ShelfNewDto(shelfNew)).toList();
        this.createdDate = bookshelf.getCreatedDate();
        this.lastModifiedDate = bookshelf.getLastModifiedDate();
    }

}
