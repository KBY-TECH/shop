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

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderDetails> orderDetails;

    @Builder
    public Orders(Users user, Long totalPrice, Long deliveryFee, Coupon coupon){
        this.user = user;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.coupon = coupon;
        this.orderStatus = OrderStatus.NEW;
    }

    public void update(OrderStatus status){
        this.orderStatus = status;
    }
}
