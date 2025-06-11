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

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL)
    private List<ShelfBook> shelfBooks = new ArrayList<>();

    public Shelf() {
    }

    public Shelf(String shelfName) {
        this.shelfName = shelfName;
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
}
