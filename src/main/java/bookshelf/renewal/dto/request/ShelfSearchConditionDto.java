package bookshelf.renewal.dto.request;

import lombok.Data;

@Data
public class ShelfSearchConditionDto {
    private String keyword;
    private String shelfName;
    private String shelfMemo;
    private String creatorName;
    private Integer fromYear;
    private Integer toYear;
}
