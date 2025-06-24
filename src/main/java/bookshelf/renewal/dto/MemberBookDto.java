package bookshelf.renewal.dto;

import bookshelf.renewal.domain.MemberBook;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberBookDto {
    private Long id;
    private String memo;
    private BookDto book;
    private MemberDto member;
    private boolean thumb;
    private boolean have;

    public MemberBookDto(MemberBook memberBook) {
        this.id = memberBook.getId();
        this.memo = memberBook.getMemo();
        this.book = new BookDto(memberBook.getBook());
        this.member = new MemberDto(memberBook.getMember());
        this.thumb = memberBook.isThumb();
        this.have = memberBook.isHave();
    }

    @QueryProjection
    public MemberBookDto(Long id, String memo, BookDto book, MemberDto member, boolean thumb, boolean have) {
        this.id = id;
        this.memo = memo;
        this.book = book;
        this.member = member;
        this.thumb = thumb;
        this.have = have;
    }
}
