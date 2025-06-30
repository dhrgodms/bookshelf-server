package bookshelf.renewal.dto.record;

import java.time.LocalDateTime;

public record BookshelfShelfFlatDto(
        Long bookshelfId,
        String username,
        String bookshelfName,
        String bookshelfColor,
        Long bookshelfBookCount,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        Long shelfId,
        String shelfName,
        String recentBookCover
) {
}
