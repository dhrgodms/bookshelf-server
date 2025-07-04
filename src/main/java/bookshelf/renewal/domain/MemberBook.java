package bookshelf.renewal.domain;

import jakarta.persistence.*;

@Entity
public class MemberBook extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name = "memberbook_id")
    private Long id;
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private boolean thumb;
    private boolean have;

    public MemberBook() {
    }

    public MemberBook(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.member.addMemberBook(this);
        this.book.addMemberBook(this);
    }

    public boolean reverseHave() {
        this.have = !this.have;
        return have;
    }

    public boolean reverseThumb() {
        this.thumb = !this.thumb;
        return thumb;
    }

    public String getMemo() {
        return memo;
    }

    public String getBookTitle() {
        return book.getTitle();
    }

    public String getMemberUsername() {
        return member.getUsername();
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public boolean isThumb() {
        return thumb;
    }

    public boolean isHave() {
        return have;
    }
}
