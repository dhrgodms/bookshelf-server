package bookshelf.renewal.domain;

import bookshelf.renewal.domain.enums.BookStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_books_new")
@Getter
@NoArgsConstructor
public class MemberBookNew extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_new_id")
    private ShelfNew shelfNew;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "status")
    private BookStatus status;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public MemberBookNew(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.status = BookStatus.OWNED;  // 기본값
    }

    public MemberBookNew(Member member, Book book, Bookshelf bookshelf, ShelfNew shelfNew) {
        this.member = member;
        this.book = book;
        this.bookshelf = bookshelf;
        this.shelfNew = shelfNew;
        this.status = BookStatus.OWNED;  // 기본값
    }

    public MemberBookNew(MemberBookNew memberBookNew) {
        this.id = memberBookNew.getId();
        this.book = memberBookNew.getBook();
        this.member = memberBookNew.getMember();
        this.bookshelf = memberBookNew.getBookshelf();
        this.shelfNew = memberBookNew.getShelfNew();
        this.status = memberBookNew.getStatus();
        this.purchaseDate = memberBookNew.getPurchaseDate();
        this.notes = memberBookNew.getNotes();
    }


    public void updateLocation(Bookshelf bookshelf, ShelfNew shelfNew) {
        this.bookshelf = bookshelf;
        this.shelfNew = shelfNew;
    }

    public void updateStatus(BookStatus status) {
        this.status = status;
    }

    public void lendBook() {
        this.status = BookStatus.LENT;
    }

    public void returnBook() {
        this.status = BookStatus.OWNED;
    }
}
