package com.example.mailexchange.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isVerified")
    private boolean isVerified = false;

    @Column(name = "otp")
    private String otp;

    /**
     * @return 
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * @return 
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return 
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return 
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return 
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
