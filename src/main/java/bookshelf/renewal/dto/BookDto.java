package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookDto {
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String seriesName;
    private String cover;
    private String categoryName;
    private String link;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pubdate;

    public BookDto(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();
        this.seriesName = book.getSeriesName();
        this.cover = book.getCover();
        this.categoryName = book.getCategoryName();
        this.link = book.getLink();
        this.pubdate = book.getPubdate();
    }
}
