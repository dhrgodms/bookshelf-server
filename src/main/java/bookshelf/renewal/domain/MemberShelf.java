package bookshelf.renewal.domain;

import jakarta.persistence.*;

@Entity
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

    public MemberShelf() {
    }

    public MemberShelf(Member member, Shelf shelf) {
        this.member = member;
        this.shelf = shelf;
    }
}
