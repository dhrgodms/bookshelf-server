package bookshelf.renewal.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Shelf extends BaseDetailEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String shelfName;
    private String shelfMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creater_id")
    private Member creater;

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL)
    private List<ShelfBook> shelfBooks = new ArrayList<>();

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL)
    private List<MemberShelf> memberShelves = new ArrayList<>();

    public Shelf() {

    }

    public Shelf(String shelfName, Member creater) {
        this.shelfName = shelfName;
        this.creater = creater;
    }

    public Member getCreater() {
        return creater;
    }

    public Long getId() {
        return id;
    }

    public String getShelfName() {
        return shelfName;
    }

    public String getShelfMemo() {
        return shelfMemo;
    }

    public List<ShelfBook> getShelfBooks() {
        return shelfBooks;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public void setShelfMemo(String shelfMemo) {
        this.shelfMemo = shelfMemo;
    }

    public void addMemberShelf(MemberShelf memberShelf) {
        this.memberShelves.add(memberShelf);
    }

    public void addShelfBook(ShelfBook shelfBook) {
        this.shelfBooks.add(shelfBook);
    }
}
