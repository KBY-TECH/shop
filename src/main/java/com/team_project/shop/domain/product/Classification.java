package com.team_project.shop.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Classification {

    ACCESSORIES(4, "장신구"), FROZEN_FOOD(6, "냉동식품"), MEAT(8, "고기"), OUTER(2, "코트_점퍼"),
    PADDING(3, "패딩"), PANTS(1, "바지"),
    SEA_FOOD(7, "해산물"), TOP(0, "상의"), UNDERWEAR(5, "속옷");

    private final Integer id;
    private final String subClass;
}

