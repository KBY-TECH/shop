package com.team_project.shop.network.request;

import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.domain.product.Images;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.product.Products;
import com.team_project.shop.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
public class CartsSaveRequestDto {

    private Long productOptionId;
    private Long quantity;

    @Builder
    public CartsSaveRequestDto(Long quantity, Long productOptionId){
        this.quantity = quantity; this.productOptionId = productOptionId;
    }

    public Carts toEntity(Users user, Product_Options productOptions){
        return Carts.builder()
                .user(user)
                .productOption(productOptions)
                .quantity(this.quantity)
                .build();
    }


}
