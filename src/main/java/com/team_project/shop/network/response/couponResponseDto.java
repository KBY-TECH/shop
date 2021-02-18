package com.team_project.shop.network.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@ToString
public class couponResponseDto {
    private Long id;
    private String code;
    private Integer quantity;
    private Double percentage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public couponResponseDto(Long id, String code, Double percentage, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.code = code;
        this.percentage = percentage;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
