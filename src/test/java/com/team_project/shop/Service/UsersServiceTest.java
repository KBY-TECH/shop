package com.team_project.shop.Service;

import com.team_project.shop.Service.IFS.LoginService_IFS;
import com.team_project.shop.Service.IFS.UsersService_IFS;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.network.request.UserRequestDto;
import com.team_project.shop.network.request.UserUpdateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

import static com.team_project.shop.web.API.HttpStatusResponseEntity.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UsersServiceTest {

    @Autowired
    UsersService_IFS usersService_ifs;
    @Autowired
    LoginService_IFS loginService_ifs;

    @Autowired
    HttpSession httpSession;

    @BeforeEach
    public void init()
    {
        List<UserRequestDto> dto = Data();
        dto.forEach(requestDto -> {
            usersService_ifs.singUp(requestDto);
        });
    }
    @Test
    @DisplayName("사용자 회원 가입(이메일 중복확인 포함) ")
    public void saveTest() {
        List<UserRequestDto> dto = Data();
        dto.forEach(requestDto -> {
            ResponseEntity<HttpStatus> responseEntity = usersService_ifs.singUp(requestDto);
            Assertions.assertThat(responseEntity).isEqualTo(RESPONSE_CONFLICT);
        });
    }

    @Test
    @DisplayName("로그인 후 valid한 사용자인지 확인.")
    public void LoginValid()
    {
        UserRequestDto dto= UserRequestDto.of("aaa@naver.com", "", "abc123!");

//        ResponseEntity<HttpStatus> login = usersService_ifs.login(dto);
//        Assertions.assertThat(login).isEqualTo(RESPONSE_OK);
        SessionUser sessionUser=(SessionUser)httpSession.getAttribute("user");
        Assertions.assertThat(sessionUser).isNotNull();
        org.junit.jupiter.api.Assertions.assertEquals(sessionUser.getEmail(),dto.getEmail());
    }

    @Test
    @DisplayName("로그인 중 비밀번호가 틀린 경우.")
    public void LoginNotValid()
    {
        UserRequestDto dto= UserRequestDto.of("aaa@naver.com", "", "abc");
//        ResponseEntity<HttpStatus> login = usersService_ifs.login(dto);
//        Assertions.assertThat(login).isEqualTo(RESPONSE_UNAUTHORIZED);
        SessionUser sessionUser=(SessionUser)httpSession.getAttribute("user");
        Assertions.assertThat(sessionUser).isNull();
    }


    @Test
    @DisplayName("로그인 후에 로그아웃 할 때 세션체거 확인")
    public void PasswordUpdateTest()
    {
        LoginValid();
        usersService_ifs.logout();
        SessionUser sessionUser=(SessionUser)httpSession.getAttribute("user");
        Assertions.assertThat(sessionUser).isNull();
    }

    @Test
    @DisplayName("패스워드 변경 후 재로그인 확인")
    public void updatedPassword()
    {
        UserUpdateDto dto= UserUpdateDto.of("aaa@naver.com", "abc123!", "1234");
        ResponseEntity<HttpStatus> httpStatusResponseEntity = usersService_ifs.updatePassword(dto);
        Assertions.assertThat(httpStatusResponseEntity).isNotNull();
        Assertions.assertThat(httpStatusResponseEntity).isEqualTo(RESPONSE_OK);
    }



    public List<UserRequestDto> Data() {
        List<UserRequestDto> userRequestDtoList = new LinkedList<>();
        userRequestDtoList.add(UserRequestDto.of("aaa@naver.com", "KIM", "abc123!"));
        userRequestDtoList.add(UserRequestDto.of("bbb@naver.com", "PARK", "abc123!"));
        userRequestDtoList.add(UserRequestDto.of("ccc@naver.com", "LEE", "abc123!"));
        return userRequestDtoList;
    }

}