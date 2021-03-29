package com.team_project.shop.domain.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CommonResponse<T> extends BaseResponse {
    private int count; //데이터 갯수
    private T data;
    private String responseMessage;

    @Builder
    public CommonResponse(T data, String responseMessage) {
        this.data = data;
        this.responseMessage = responseMessage;
        if(data instanceof List) {
            this.count = ((List<?>) data).size();
        } else {
            this.count = 1;
        }
    }
}
