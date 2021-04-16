package com.team_project.shop.network.request;

import lombok.*;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class PublisherUpdatePasswordRequestDto {

    @Pattern(regexp = "^[0-9]{10}$" ,message = "10자리의 숫자로만 입력해주세요.")
    private String password;

}
