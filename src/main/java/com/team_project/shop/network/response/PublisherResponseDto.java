package com.team_project.shop.network.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PublisherResponseDto {

    private String email;

    private String BusinessNumber;


    @Builder
    public PublisherResponseDto(String email, String businessNumber) {
        this.email = email;
        BusinessNumber = businessNumber;
    }

}
