package com.team_project.shop.domain.coupon;

import org.assertj.core.api.Assertions;
import org.hibernate.dialect.lock.AbstractSelectLockingStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponTest {

    @Autowired CouponRepository couponRepository;

    @Test
    public void create(){
        Coupon coupon=Coupon.builder()
                .code(UUID.randomUUID().toString())
                .quantity(100)
                .percentage(0.1)
                .build();
        Coupon couponCreate=couponRepository.save(coupon);
        Assert.assertNotNull(couponCreate);
    }
    @Test
    public void read()
    {
        Coupon coupon=Coupon.builder()
                .code(UUID.randomUUID().toString())
                .quantity(100)
                .percentage(0.5)
                .build();
        Coupon couponCreate=couponRepository.save(coupon);

        if(couponCreate.isActivate())
        {
            Optional<Coupon> createRead=couponRepository.findById(couponCreate.getId());
            Assertions.assertThat(createRead.get().getPercentage()).isEqualTo(couponCreate.getPercentage());
        }
    }

    @Test
    @DisplayName("coupon 재고 수량 할인율 변경.")
    public void Update()
    {
        Coupon coupon=Coupon.builder()
                .code(UUID.randomUUID().toString())
                .quantity(100)
                .percentage(0.5)
                .build();
        Coupon couponCreate=couponRepository.save(coupon);

        Integer inputDay=10;
        if(couponCreate.isActivate())
        {
            couponCreate.update(30,0.7, inputDay);
            Optional<Coupon> read=couponRepository.findByCode(couponCreate.getCode());
            Assert.assertNotNull(read);
            if(read.isPresent()) {
                org.junit.jupiter.api.Assertions.assertThrows(org.opentest4j.AssertionFailedError.class,() -> Assertions.assertThat(couponCreate.getEndTime()).isEqualTo(read.get().getEndTime()) );
                Assertions.assertThat(couponCreate.getCode()).isEqualTo(read.get().getCode());
            }
        }
    }

    @Test
    public void Delete()
    {
        Coupon coupon=Coupon.builder()
                .code(UUID.randomUUID().toString())
                .quantity(100)
                .percentage(0.5)
                .build();
        Coupon couponCreate=couponRepository.save(coupon);

        String tempCode=couponCreate.getCode();
        couponRepository.delete(couponCreate);
        Optional<Coupon> readCoupon=couponRepository.findByCode(tempCode);
        org.junit.jupiter.api.Assertions.assertThrows(java.util.NoSuchElementException.class,() -> readCoupon.get());
    }
}