package com.team_project.shop.network.request;

import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ToString
public class UserRequestDto {
    @NotEmpty(message = "이름을 입력하세요.")
    private String name;

    @NotEmpty
    @Email(message = "이메일 형식을 올바르게 입력해주세요.")
    private String email;  // username

    @NotEmpty
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    public void encrypt(PasswordEncoder passwordEncoder)
    {
        this.password=passwordEncoder.encode(this.password);
    }


    public Users toEntity()
    {
        return Users.of(name,email,Role.USER,password);
    }

    public void add()
    {
        this.password+="{bcrypt}";
    }

}
