package bookshelf.renewal.domain;

import jakarta.persistence.*;

@Entity
public class ShelfBook extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "shelfBook_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    public ShelfBook() {
    }

    public ShelfBook(Book book, Shelf shelf) {
        this.book = book;
        this.shelf = shelf;
    }
}
