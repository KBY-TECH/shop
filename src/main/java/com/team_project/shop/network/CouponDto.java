package com.team_project.shop.network;

import com.team_project.shop.domain.coupon.Coupon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CouponDto {

    private Long id;
    private String code;
    private Integer quantity;
    private Double percentage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public CouponDto(Coupon coupon) {
        this.id = coupon.getId();
        this.code = coupon.getCode();
        this.percentage = coupon.getPercentage();
        this.startTime = coupon.getStartTime();
        this.endTime = coupon.getEndTime();
    }

}
