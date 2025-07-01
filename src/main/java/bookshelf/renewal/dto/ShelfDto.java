package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Shelf;
import bookshelf.renewal.dto.response.SimpleShelfBookDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShelfDto {
    private Long id;
    private String shelfName;
    private String shelfMemo;
    private MemberDto creator;
//    private List<ShelfBook> shelfBooks = new ArrayList<>();
    private List<String> memberShelves = new ArrayList<>();
    private List<BookDto> books = new ArrayList<>();
    private List<SimpleShelfBookDto> shelfBooks = new ArrayList<>();
    private PageInfoDto pageInfoDto;

    public ShelfDto() {
    }

    public ShelfDto(String shelfName, String shelfMemo) {
        this.shelfName = shelfName;
        this.shelfMemo = shelfMemo;
    }

    public ShelfDto(String shelfName, String shelfMemo, MemberDto creator, List<String> memberShelves) {
        this.shelfName = shelfName;
        this.shelfMemo = shelfMemo;
        this.creator = creator;
        this.memberShelves = memberShelves;
    }

    @QueryProjection
    public ShelfDto(Long id, String shelfName, String shelfMemo) {
        this.id = id;
        this.shelfName = shelfName;
        this.shelfMemo = shelfMemo;
    }

    @QueryProjection
    public ShelfDto(String shelfName, String shelfMemo, MemberDto creator) {
        this.shelfName = shelfName;
        this.shelfMemo = shelfMemo;
        this.creator = creator;
    }

    @QueryProjection
    public ShelfDto(Long id, String shelfName, String shelfMemo, MemberDto creator) {
        this.id = id;
        this.shelfName = shelfName;
        this.shelfMemo = shelfMemo;
        this.creator = creator;
    }


    public ShelfDto(Shelf shelf) {
        this.id = shelf.getId();
        this.shelfName = shelf.getShelfName();
        this.shelfMemo = shelf.getShelfMemo();
        this.creator = new MemberDto(shelf.getCreator().getUsername());
        this.memberShelves = shelf.getMemberShelves().stream().map(m -> m.getMember().getUsername()).toList();
    }

    public Long getId() {
        return id;
    }
}
