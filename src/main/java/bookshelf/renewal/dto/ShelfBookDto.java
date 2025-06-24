package bookshelf.renewal.dto;


import bookshelf.renewal.domain.ShelfBook;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ShelfBookDto {
    private Long id;
    private BookDto book;
    private ShelfDto shelf;

    public ShelfBookDto(ShelfBook shelfBook) {
        this.id = shelfBook.getId();
        this.book = new BookDto(shelfBook.getBook());
        this.shelf = new ShelfDto(shelfBook.getShelf());
    }

    @QueryProjection
    public ShelfBookDto(Long id, BookDto book, ShelfDto shelf) {
        this.id = id;
        this.book = book;
        this.shelf = shelf;
    }
}
