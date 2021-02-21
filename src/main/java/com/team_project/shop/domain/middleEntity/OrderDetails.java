package com.team_project.shop.domain.middleEntity;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.order.Orders;
import com.team_project.shop.domain.product.Product_Options;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
@Entity
public class OrderDetails extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name= "product_option_id")
    private Product_Options productOption;

    private Long quantity;

    @Builder
    public OrderDetails(Orders order, Product_Options productOption, Long quantity){
        this.order = order;
        this.productOption = productOption;
        this.quantity = quantity;
    }

}
