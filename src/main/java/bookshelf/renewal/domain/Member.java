package bookshelf.renewal.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberShelf> memberShelves = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberBook> memberBooks = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }

    public Member() {
    }
}
