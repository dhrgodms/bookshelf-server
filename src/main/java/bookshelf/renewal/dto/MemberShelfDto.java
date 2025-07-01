package bookshelf.renewal.dto;

import bookshelf.renewal.domain.MemberShelf;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class MemberShelfDto {
    private Long id;
    private MemberDto member;
    private ShelfDto shelf;
    private LocalDateTime savedDate;

    public MemberShelfDto(MemberShelf memberShelf) {
        this.id = memberShelf.getId();
        this.member = new MemberDto(memberShelf.getMember());
        this.shelf = new ShelfDto(memberShelf.getShelf());
    }

    @QueryProjection
    public MemberShelfDto(Long id, MemberDto member, ShelfDto shelf, LocalDateTime savedDate) {
        this.id = id;
        this.member = member;
        this.shelf = shelf;
        this.savedDate = savedDate;
    }

    public Long getId() {
        return id;
    }

    public MemberDto getMember() {
        return member;
    }

    public ShelfDto getShelf() {
        return shelf;
    }
}
