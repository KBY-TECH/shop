package com.team_project.shop.network.request;

import com.team_project.shop.domain.Publisher.Publisher;
import com.team_project.shop.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ToString
public class PublisherCreateRequestDto {

    @NotEmpty
    @Email(message = "이메일 형식을 올바르게 입력해주세요.")
    private String email;  // username

    @NotEmpty(message = "이름을 입력하세요.")
    private String name;

    @NotEmpty(message = "상호명을 입력해주세요")
    private String businessName;


    @Pattern(regexp = "^[0-9]{10}$" ,message = "10자리의 숫자로만 입력해주세요.")
    private String businessNumber;


    @NotEmpty
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;


    public Publisher toEntity(PasswordEncoder passwordEncoder)
    {
        return Publisher.builder()
                .email(this.email).name(name).businessNumber(businessNumber).businessName(businessName).role(Role.PUBLISHER).password(passwordEncoder.encode(password))
                .build();
    }
}
