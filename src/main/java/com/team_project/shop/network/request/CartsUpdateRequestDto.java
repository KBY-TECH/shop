package com.team_project.shop.network.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartsUpdateRequestDto {
    private Long quantity;

    @Builder
    public CartsUpdateRequestDto(Long quantity){
        this.quantity = quantity;
    }
}
