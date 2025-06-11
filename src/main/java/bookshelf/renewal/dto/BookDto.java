package bookshelf.renewal.dto;

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
}
