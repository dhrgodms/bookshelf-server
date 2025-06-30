package bookshelf.renewal.dto.record;

public record BookshelfWithCountDto(
        Long bookshelfId,
        String name,
        Long bookCount
) {}

