package bookshelf.renewal.dto;

import bookshelf.renewal.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JoinResponseDto {
    private Long memberId;
    private String email;
    private String picture;

    public JoinResponseDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
