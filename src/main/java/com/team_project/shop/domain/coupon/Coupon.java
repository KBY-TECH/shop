package com.team_project.shop.domain.coupon;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.middleEntity.User_Coupon;
import com.team_project.shop.domain.product.Products;
import com.team_project.shop.network.response.couponResponseDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class Coupon extends BaseEntity {
    // 동시성 이슈에 대비하여 만든 Map 그리고 코드 쿠폰의 unique한 특성을 살리기 위해 공유 변수 선언.
    private static ConcurrentMap<String, Integer> codeDuplicateCheck = new ConcurrentHashMap<>();
    private String code;
    private Integer quantity;
    private Double percentage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean activate; // list 와 quantity 비교하여 쿠폰을 활성화 비활성화 여부 판단.

    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<User_Coupon> userCouponList;

    @Builder
    public Coupon(String code, Integer quantity, Double percentage) {
        this.code = codeCheking(UUID.randomUUID().toString()).toString().substring(0, 8);
        this.quantity = quantity;
        this.percentage = percentage;
        this.startTime = LocalDateTime.now();
        this.endTime = LocalDateTime.now().plusDays(30);
        this.activate = true;
    }
    // 코드 중복이 체크하기 위해 랜덤 값 생성,
    private String codeCheking(String validCode) {
        while (true) {
            if (!codeDuplicateCheck.containsKey(validCode)) {
                return validCode;
            }
            validCode=UUID.randomUUID().toString();
        }
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "code='" + code + '\'' +
                ", quantity=" + quantity +
                ", percentage=" + percentage +
                ", startTime=" + startTime +
                ", endTime=" + customFormat() +
                ", activate=" + activate +
                ", userCouponList=" + userCouponList +
                '}';
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public couponResponseDto toEntity() {
        return couponResponseDto.builder()
                .id(getId())
                .code(code)
                .percentage(percentage)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    // 종료 일자 client에게 보여주기
    public String customFormat() {
        return endTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public Coupon update(Integer quantity,
                         Double percentage,
                         Integer day) {
        this.quantity = quantity;
        this.percentage = percentage;
        this.endTime = endTime.plusDays(day);
        return this;
    }
}
