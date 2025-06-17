package bookshelf.renewal.dto;

import bookshelf.renewal.domain.MemberShelf;
import lombok.Data;

@Data
public class MemberShelfDto {
    private Long id;
    private MemberDto member;
    private ShelfDto shelf;

    public MemberShelfDto(MemberShelf memberShelf) {
        this.id = memberShelf.getId();
        this.member = new MemberDto(memberShelf.getMember());
        this.shelf = new ShelfDto(memberShelf.getShelf());
    }
}
