package com.team_project.shop.domain.Publisher;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Entity
public class Publisher extends BaseEntity implements UserDetails {


    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String businessNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password;


    @Builder
    public Publisher(String email, String name,String businessNumber,String businessName, Role role, String password) {
        this.email = email;
        this.businessNumber = businessNumber;
        this.role = role;
        this.businessName=businessName;
        this.password = password;
        this.name=name;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
