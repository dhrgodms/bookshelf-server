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

    @Column(name="bookshelf_color")
    private String bookshelfColor;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public Bookshelf(Member member, String bookshelfName) {
        this.member = member;
        this.bookshelfName = bookshelfName;
    }

    public Bookshelf(Member member, String bookshelfName, String bookshelfColor) {
        this.member = member;
        this.bookshelfName = bookshelfName;
        this.bookshelfColor = bookshelfColor;
    }

    public Bookshelf(Member member, String bookshelfName, String bookshelfColor, String notes) {
        this.member = member;
        this.bookshelfName = bookshelfName;
        this.bookshelfColor = bookshelfColor;
        this.notes = notes;
    }

    public void addShelf(ShelfNew shelf) {
        shelves.add(shelf);
        shelf.setBookshelf(this);
    }
}
