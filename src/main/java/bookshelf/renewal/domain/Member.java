package bookshelf.renewal.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberShelf> memberShelves = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberBook> memberBooks = new ArrayList<>();

    @OneToMany(mappedBy = "creater", cascade = CascadeType.ALL)
    private List<Shelf> ownShelves = new ArrayList<>();

    public Member(String username) {
        this.username = username;
    }

    public Member() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void addMemberShelf(MemberShelf memberShelf) {
        this.memberShelves.add(memberShelf);
    }

    public void addMemberBook(MemberBook memberBook) {
        this.memberBooks.add(memberBook);
    }

    public void addOwnShelf(Shelf shelf) {
        this.ownShelves.add(shelf);
    }
}
