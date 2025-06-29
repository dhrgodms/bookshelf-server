package bookshelf.renewal.dto.request;

import lombok.Data;

@Data
public class BookSearchConditionDto {
    private String keyword;
    private String author;
    private String publisher;
    private Integer fromYear;
    private Integer toYear;
}
