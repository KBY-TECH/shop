package com.team_project.shop.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProductState {
    ONSALE("판매 중"),
    SUSPENSION("판매중지"),
    OUTOFSTOCK("품절");

    private final String text;
    //string을 넣으면 enum값을 리턴
    public static ProductState find(String state){
        return Arrays.stream(values())
                .filter(obj -> obj.text.equals(state))
                .findFirst()
                .orElse(ONSALE);
    }
    public String toText() {
        return this.text;
    }
}