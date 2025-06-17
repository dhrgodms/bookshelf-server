package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Member;
import lombok.Data;

@Data
public class MemberDto {
    private String username;

    public MemberDto(){}

    public MemberDto(Member member) {
        this.username = member.getUsername();
    }
}
