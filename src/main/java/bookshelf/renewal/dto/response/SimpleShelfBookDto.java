package bookshelf.renewal.dto.response;

import bookshelf.renewal.dto.BookDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SimpleShelfBookDto {
    private Long shelfBookId;
    private Long shelfId;
    private BookDto bookDto;

    @QueryProjection
    public SimpleShelfBookDto(Long shelfBookId, Long shelfId, BookDto bookDto) {
        this.shelfBookId = shelfBookId;
        this.shelfId = shelfId;
        this.bookDto = bookDto;
    }
}
