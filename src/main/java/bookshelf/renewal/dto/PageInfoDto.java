package bookshelf.renewal.dto;

import lombok.Getter;

@Getter
public class PageInfoDto {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PageInfoDto(int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
