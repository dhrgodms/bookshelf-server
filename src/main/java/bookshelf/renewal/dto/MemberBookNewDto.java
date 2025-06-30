package bookshelf.renewal.dto;

import bookshelf.renewal.domain.*;
import bookshelf.renewal.domain.enums.BookStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberBookNewDto {
    private Long id;
    private BookDto book;
    private MemberDto member;
    private BookshelfDto bookshelf;
    private ShelfNewDto shelfNew;
    private BookStatus status;
    private LocalDateTime purchaseDate;
    private String notes;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public MemberBookNewDto(MemberBookNew memberBookNew) {
        this.id = memberBookNew.getId();
        this.book = new BookDto(memberBookNew.getBook());
        this.member = new MemberDto(memberBookNew.getMember());
        this.bookshelf = new BookshelfDto(memberBookNew.getBookshelf());
        this.shelfNew = new ShelfNewDto(memberBookNew.getShelfNew());
        this.status = memberBookNew.getStatus();
        this.purchaseDate = memberBookNew.getPurchaseDate();
        this.notes = memberBookNew.getNotes();
        this.createdDate = memberBookNew.getCreatedDate();
        this.lastModifiedDate = memberBookNew.getLastModifiedDate();
    }

    @QueryProjection
    public MemberBookNewDto(Long id, BookDto book, MemberDto member, BookshelfDto bookshelf, ShelfNewDto shelfNew, BookStatus status, LocalDateTime purchaseDate, String notes, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.bookshelf = bookshelf;
        this.shelfNew = shelfNew;
        this.status = status;
        this.purchaseDate = purchaseDate;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
