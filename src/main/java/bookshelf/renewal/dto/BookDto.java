package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Book;
import com.fasterxml.jackson.databind.JsonNode;
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

    protected BookDto() {
    }

    public BookDto(String title) {
        this.title = title;
    }


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


    public static BookDto getBookDto(JsonNode book){
        String title = book.get("title").asText();
        String author = book.get("author").asText();
        String publisher = book.get("publisher").asText();
        String isbn = book.get("isbn").asText();
        String seriesName = book.has("seriesInfo") ? book.get("seriesInfo").get("seriesName").asText() : "";
        String cover = book.get("cover").asText();
        String categoryName = book.get("categoryName").asText();
        String link = book.get("link").asText();
        String pubDate = book.get("pubDate").asText();
        return new BookDto(title, author, publisher, isbn, seriesName, cover, categoryName, link, LocalDate.parse(pubDate));
    }
}
