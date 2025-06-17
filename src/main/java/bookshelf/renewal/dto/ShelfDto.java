package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Shelf;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShelfDto {
    private String shelfName;
    private String shelfMemo;
    private MemberDto creator;
//    private List<ShelfBook> shelfBooks = new ArrayList<>();
    private List<String> memberShelves = new ArrayList<>();

    public ShelfDto() {
    }

    public ShelfDto(String shelfName, MemberDto creator) {
        this.shelfName = shelfName;
        this.creator = creator;
    }

    public ShelfDto(Shelf shelf) {
        this.shelfName = shelf.getShelfName();
        this.shelfMemo = shelf.getShelfMemo();
        this.creator = new MemberDto(shelf.getCreator().getUsername());
        this.memberShelves = shelf.getMemberShelves().stream().map(m -> m.getMember().getUsername()).toList();
    }
}
