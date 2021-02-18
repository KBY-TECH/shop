package com.team_project.shop.domain.coupon;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.middleEntity.User_Coupon;
import com.team_project.shop.domain.product.Products;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class Coupon extends BaseEntity {
    private String code;
    private Integer quantity;
    private Double percentage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean activate; // list 와 quantity 비교하여 쿠폰을 활성화 비활성화 여부 판단.

    @OneToMany(mappedBy = "coupon",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<User_Coupon>userCouponList;

    @Builder
    public Coupon(String code, Integer quantity, Double percentage) {
        this.code = code;
        this.quantity = quantity;
        this.percentage = percentage;
        this.startTime = LocalDateTime.now();
        this.endTime=LocalDateTime.now().plusDays(30);
        this.activate=true;
    }
    public void setEndTime(LocalDateTime endTime)
    {
        this.endTime=endTime;
    }
    // 종료 일자 client에게 보여주기
    public String customFormat()
    {
        return endTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

}
