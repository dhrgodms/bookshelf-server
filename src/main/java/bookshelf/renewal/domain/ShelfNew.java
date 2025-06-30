package bookshelf.renewal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="shelves_new")
@NoArgsConstructor
@Getter
public class ShelfNew extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "shelf_new_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @Column(name = "shelf_position", nullable = false)
    private Integer shelfPosition;

    @Column(name = "shelf_custom_name")
    private String shelfCustomName;

    public ShelfNew(Bookshelf bookshelf, Integer shelfPosition) {
        this.bookshelf = bookshelf;
        this.shelfPosition = shelfPosition;
    }

    public void setShelfCustomName(String shelfCustomName) {
        this.shelfCustomName = shelfCustomName;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }
}