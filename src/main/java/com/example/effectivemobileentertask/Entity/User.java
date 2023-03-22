package com.example.effectivemobileentertask.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "balance")
    private double balance;
    @Column(name = "active")
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany
    @JoinColumn
    private Set<Product> products;

    public User(String username, String email, String password, double balance, boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                ", roles=" + roles +
                ", products=" + products +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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
        return isActive();
    }
}
