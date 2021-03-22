package com.team_project.shop.domain.order;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.coupon.Coupon;
import com.team_project.shop.domain.middleEntity.OrderDetails;
import com.team_project.shop.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Orders extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private Long totalPrice;

    private Long deliveryFee;

    private Long totalPayment;

    private String destination;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderDetails> orderDetails;

    @Builder
    public Orders(Users user, Long totalPrice, Long deliveryFee, Coupon coupon, String destination, Long totalPayment){
        this.user = user;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.totalPayment = totalPayment;
        this.coupon = coupon;
        this.orderStatus = OrderStatus.NEW;
        this.destination = destination;
    }

    public void update(OrderStatus status){
        this.orderStatus = status;
    }

    public void addCoupon(Coupon coupon){this.coupon = coupon;}
}
