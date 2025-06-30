package bookshelf.renewal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Bookshelf extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "bookshelf_name", nullable = false)
    private String bookshelfName;

    @OneToMany(mappedBy = "bookshelf", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShelfNew> shelves = new ArrayList<>();

    public Bookshelf(Member member, String bookshelfName) {
        this.member = member;
        this.bookshelfName = bookshelfName;
    }

    public Bookshelf(Bookshelf bookshelf) {
        this.id = bookshelf.getId();
        this.member = bookshelf.getMember();
        this.bookshelfName = bookshelf.getBookshelfName();
        this.shelves = bookshelf.getShelves();
    }

    public void addShelf(ShelfNew shelf) {
        shelves.add(shelf);
        shelf.setBookshelf(this);
    }
}
