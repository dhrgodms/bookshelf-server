package bookshelf.renewal.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MemberShelf extends BaseDetailEntity{
    @Id
    @GeneratedValue
    @Column(name = "memberShelf_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    protected MemberShelf() {
    }

    public MemberShelf(Member member, Shelf shelf) {
        this.member = member;
        this.shelf = shelf;
        this.member.addMemberShelf(this);
        this.shelf.addMemberShelf(this);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Shelf getShelf() {
        return shelf;
    }


}
