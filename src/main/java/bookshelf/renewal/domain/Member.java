package bookshelf.renewal.domain;

import bookshelf.renewal.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    @Column(unique = true)
    private String email;

    private String password;
    private String picture = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private Role role = Role.USER;

    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Member(Long id, String username, String email, String password, String picture, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberShelf> memberShelves = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberBook> memberBooks = new ArrayList<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Shelf> ownShelves = new ArrayList<>();

    public Long getId(){return id;}
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPicture() {
        return picture;
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

    public Role getRole() {
        return this.role;
    }
}
