package com.team_project.shop.network.request;

import com.team_project.shop.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrdersUpdateRequestDto {
    private OrderStatus orderStatus;

    @Builder
    public OrdersUpdateRequestDto(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }
}
