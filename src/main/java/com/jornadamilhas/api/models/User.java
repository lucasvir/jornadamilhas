package com.jornadamilhas.api.models;

import com.jornadamilhas.api.dto.user.UserCreateDto;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity(name = "User")
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String imgUrl;
    private String password;
    private String old_password;

    @OneToMany
    private List<Comment> comments;

    public User() {

    }

    public User(Long id, String name, String email, String imgUrl, String password, String old_password, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imgUrl = imgUrl;
        this.password = password;
        this.old_password = old_password;
        this.comments = comments;
    }

    public User(UserCreateDto dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.imgUrl = dto.imgUrl();
        this.password = hashPass(dto.password());
        this.old_password = password;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getOld_password() {
        return old_password;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", password='" + password + '\'' +
                ", old_password='" + old_password + '\'' +
                ", comments=" + comments +
                '}';
    }

    private String hashPass(String password) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return bcrypt.encode(password);
    }
}
