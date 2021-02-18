package com.team_project.shop.network.request;

import com.team_project.shop.domain.coupon.Coupon;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class couponRequestDto {
    private Long id;
    private String code;
    private Integer quantity;
    private Double percentage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public couponRequestDto(Long id, String code, Integer quantity, Double percentage, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.code = code;
        this.quantity = quantity;
        this.percentage = percentage;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public  Coupon toEntity()
    {
        return Coupon.builder()
                .code(code)
                .percentage(percentage)
                .quantity(quantity)
                .build();
    }

}
