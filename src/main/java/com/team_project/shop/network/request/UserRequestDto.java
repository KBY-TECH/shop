package com.team_project.shop.network.request;

import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserRequestDto {
    private String email;
    private String name;
    private String password;


    public void setPassword(String password) {
        this.password = password;
    }

    public Users toEntity()
    {
        return Users.of(name,email,Role.USER,password);
    }

}
