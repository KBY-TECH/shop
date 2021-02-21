package com.team_project.shop.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    NEW("주문 완료"),
    CANCEL("주문 취소"),
    COMPLETE("결제 완료"),
    RETURN("반품");

    private final String title;
}
