package com.team_project.shop.network.response;

import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartsResponseDto {
    private Long id;
    private Users user;
    private Product_Options productOption;
    private Long quantity;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public CartsResponseDto(Carts carts){
        this.id = carts.getId();
        this.user = carts.getUser();
        this.productOption = carts.getProductOption();
        this.quantity = carts.getQuantity();
        this.createdDate = carts.getFormattiedCreateDate();
        this.modifiedDate = carts.getFormattiedModifyDate();
    }
}
