package com.team_project.shop.domain.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse extends BaseResponse {
    private String errorMessage;
    private int errorCode;

    @Builder
    public ErrorResponse( int errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
