package com.team_project.shop.domain.Address;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
@Entity
public class Address extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String address;

    @Builder
    public Address(Users user, String address){
        this.user = user;
        this.address = address;
    }
}
