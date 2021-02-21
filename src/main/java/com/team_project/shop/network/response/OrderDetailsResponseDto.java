package com.team_project.shop.network.response;

import com.team_project.shop.domain.middleEntity.OrderDetails;
import com.team_project.shop.domain.order.Orders;
import com.team_project.shop.domain.product.Product_Options;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderDetailsResponseDto {
    private Long id;
    private Orders order;
    private Product_Options productOption;
    private Long quantity;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public OrderDetailsResponseDto(OrderDetails orderDetails){
        this.id = orderDetails.getId();
        this.order = orderDetails.getOrder();
        this.productOption = orderDetails.getProductOption();
        this.quantity = orderDetails.getQuantity();
        this.createdDate = orderDetails.getFormattiedCreateDate();
        this.modifiedDate = orderDetails.getFormattiedModifyDate();
    }
}
