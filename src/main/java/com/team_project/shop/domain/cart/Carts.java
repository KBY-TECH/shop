package com.team_project.shop.domain.cart;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class Carts extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_option_id")
    private Product_Options productOption;

    private Long quantity;

    @Builder
    public Carts(Users user, Product_Options productOption, Long quantity){
        this.user = user;
        this.productOption = productOption;
        this.quantity = quantity;
    }

    public void update(Long quantity, Product_Options productOption){
        this.quantity = quantity;
        this.productOption = productOption;
    }
}
