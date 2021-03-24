package com.team_project.shop.Service.IFS;

import com.team_project.shop.domain.user.Users;
import com.team_project.shop.network.request.UserRequestDto;
import com.team_project.shop.network.request.UserUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UsersService_IFS {

    public ResponseEntity<HttpStatus> singUp(UserRequestDto requestDto);

    public boolean isDuplicateEmail(String email);

    public boolean isValidMember(UserRequestDto requestDto, PasswordEncoder passwordEncoder);

     public ResponseEntity<HttpStatus> updatePassword(UserUpdateDto requestDto);


    public ResponseEntity<HttpStatus> login(UserRequestDto requestDto);

    public ResponseEntity<HttpStatus>  logout();

}
