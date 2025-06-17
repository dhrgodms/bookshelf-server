package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {
    private String username;

    public MemberDto(){}

    @QueryProjection
    public MemberDto(String username) {
        this.username = username;
    }

    public MemberDto(Member member) {
        this.username = member.getUsername();
    }

    public String getUsername() {
        return username;
    }
}
