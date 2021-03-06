package com.team_project.shop.domain.user;

import com.team_project.shop.domain.Address.Address;
import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.middleEntity.User_Coupon;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Getter
@ToString
@DynamicUpdate
@Entity
public class Users extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = true)
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<User_Coupon> userCouponList;

    private String defaultAddress;

    @Builder
    public Users(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    private Users (String name, String email, Role role,String password) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password=password;
    }

    public static Users of(String name,String email,Role role,String password)
    {
        return new Users(name,email,role,password);
    }



    public Users update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public Users updatePassword(String password)
    {
        this.password=password;
        return this;
    }

    public void changeDefaultAddress(String address){
        this.defaultAddress = address;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
