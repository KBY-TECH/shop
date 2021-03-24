package com.team_project.shop.network.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserUpdateDto {

    private String email;
    private String orgPassword;
    private String updatingPassword;

    
}
