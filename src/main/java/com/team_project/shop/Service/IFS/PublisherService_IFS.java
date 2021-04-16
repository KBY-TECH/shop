package com.team_project.shop.Service.IFS;

import com.team_project.shop.network.request.PublisherCreateRequestDto;
import com.team_project.shop.network.request.PublisherUpdatePasswordRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PublisherService_IFS extends UserDetailsService {

    public ResponseEntity<HttpStatus> signUp(PublisherCreateRequestDto requestDto);

    public boolean isDuplicateEmail(String email);

    public boolean isDuplicateBusinessNumber(String businessNumber);

    public ResponseEntity<HttpStatus> updatePassword(PublisherUpdatePasswordRequestDto requestDto);

    public ResponseEntity<HttpStatus> read(Long id);

    public ResponseEntity<HttpStatus> delete(Long id);
}
