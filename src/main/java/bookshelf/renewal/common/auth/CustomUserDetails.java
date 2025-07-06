package bookshelf.renewal.common.auth;

import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Long memberId;
    private final String username;
    private final String email;
    private final String password;
    private final String picture;
    private final Role role;
    private final Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(Long memberId, String username, String email, String picture, Role role) {
        this.memberId = memberId;
        this.username = username;
        this.email = email;
        this.password = "";
        this.picture = picture;
        this.role = role;
        authorities = List.of(new SimpleGrantedAuthority(role.name()));
    }

    public CustomUserDetails(Member member) {
        this.memberId = member.getId();
        this.username = member.getUsername();
        this.email = member.getUsername();
        this.password = "";
        this.picture = member.getPicture();
        this.role = member.getRole();
        authorities = List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public Long getMemberId() {
        return memberId;
    }

}
