package com.team_project.shop.network.request;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PublisherUpdatePasswordRequestDto {
    private String password;

    @Builder
    public PublisherUpdatePasswordRequestDto(String password) {
        this.password = password;
    }
}
