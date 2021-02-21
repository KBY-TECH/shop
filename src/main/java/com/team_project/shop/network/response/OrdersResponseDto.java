package com.team_project.shop.network.response;

import com.team_project.shop.domain.coupon.Coupon;
import com.team_project.shop.domain.order.OrderStatus;
import com.team_project.shop.domain.order.Orders;
import com.team_project.shop.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrdersResponseDto {
    private Long id;
    private Users user;
    private Long totalPrice;
    private Long deliveryFee;
    private OrderStatus orderStatus;
    private Coupon coupon;
    private List<OrderDetailsResponseDto> orderDetailsResponseDtos;
    private String createdDate;
    private String modifiedDate;
    @Builder
    public OrdersResponseDto(Orders orders){
        this.id = orders.getId();
        this.user = orders.getUser();
        this.totalPrice = orders.getTotalPrice();
        this.deliveryFee = orders.getDeliveryFee();
        this.orderStatus = orders.getOrderStatus();
        this.coupon = orders.getCoupon();
        this.orderDetailsResponseDtos = orders.getOrderDetails()
                .stream().map(OrderDetailsResponseDto::new).collect(Collectors.toList());
        this.createdDate = orders.getFormattiedCreateDate();
        this.modifiedDate = orders.getFormattiedModifyDate();
    }
}
