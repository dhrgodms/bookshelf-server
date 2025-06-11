package bookshelf.renewal.domain;

import jakarta.persistence.*;

@Entity
public class MemberBook extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public MemberBook() {
    }

    public MemberBook(Member member, Book book) {
        this.member = member;
        this.book = book;
    }
}
