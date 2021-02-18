package com.team_project.shop.domain.middleEntity;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.coupon.Coupon;
import com.team_project.shop.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class User_Coupon extends BaseEntity {

    @ManyToOne
    private Users user;

    @ManyToOne
    private Coupon coupon;

}
