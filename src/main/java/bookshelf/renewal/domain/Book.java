package bookshelf.renewal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String seriesName;
    private String cover;
    private String link;
    private String categoryName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pubdate;

    @OneToMany(mappedBy = "book")
    private List<MemberBook> memberBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<ShelfBook> shelfBooks = new ArrayList<>();

    protected Book() {

    }

    public Book(String title){
        this.title = title;
    }


    public Book(String title, String author, String publisher, String isbn, String seriesName, String cover, String link, String categoryName, LocalDate pubdate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.seriesName = seriesName;
        this.cover = cover;
        this.link = link;
        this.categoryName = categoryName;
        this.pubdate = pubdate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public String getCover() {
        return cover;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getLink() {
        return link;
    }

    public LocalDate getPubdate() {
        return pubdate;
    }

    public void addMemberBook(MemberBook memberBook) {
        this.memberBooks.add(memberBook);
    }

    public void addShelfBook(ShelfBook shelfBook) {
        this.shelfBooks.add(shelfBook);
    }
}
