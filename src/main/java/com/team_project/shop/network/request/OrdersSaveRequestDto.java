package com.team_project.shop.network.request;

import com.team_project.shop.domain.coupon.Coupon;
import com.team_project.shop.domain.middleEntity.OrderDetails;
import com.team_project.shop.domain.order.OrderStatus;
import com.team_project.shop.domain.order.Orders;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.network.CouponDto;
import com.team_project.shop.network.response.OrderDetailsResponseDto;
import com.team_project.shop.network.response.couponResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class OrdersSaveRequestDto {

    private Long totalPrice;

    private Long deliveryFee;

    private Long totalPayment;

    private String destination;

    private Long couponId;
    //유저, 쿠폰, 오더디테일 리스트..?
    private List<Long> cartId;


    @Builder
    public OrdersSaveRequestDto(Long totalPrice, Long deliveryFee, Long totalPayment, String destination,  List<Long> cartId, Long couponId){
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.totalPayment = totalPayment;
        this.destination = destination;
        this.cartId = cartId;
        this.couponId = couponId;   // 해당없음은 디폴트 0 값
    }

    //쿠폰은 null가능 함으로 주문 저장 후 업데이트로 추가하는 것으로 결정
    public Orders toEntity(Users user){
        return  Orders.builder()
                .user(user)
                .totalPrice(this.totalPrice)
                .deliveryFee(this.deliveryFee)
                .totalPayment(this.totalPayment)
                .destination(this.destination)
                .build();
    }
}
