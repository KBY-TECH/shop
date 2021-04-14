package com.team_project.shop.Service;

import com.team_project.shop.Service.IFS.LoginService_IFS;
import com.team_project.shop.Service.IFS.Publisher_IFS;
import com.team_project.shop.domain.Publisher.Publisher;
import com.team_project.shop.domain.Publisher.PublisherRepository;
import com.team_project.shop.network.request.PublisherCreateRequestDto;
import com.team_project.shop.network.request.PublisherUpdatePasswordRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.team_project.shop.network.response.HttpStatusResponseEntity.RESPONSE_OK;

@RequiredArgsConstructor
@Slf4j
@Service
public class PublisherService implements Publisher_IFS {
    private final PublisherRepository publisherRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginService_IFS loginService;

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> signUp(PublisherCreateRequestDto requestDto) {
        publisherRepository.save(requestDto.toEntity(passwordEncoder));
        return RESPONSE_OK;
    }

    @Override
    public ResponseEntity<HttpStatus> updatePassword(PublisherUpdatePasswordRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> read(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        return null;
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        return publisherRepository.findByEmail(email).isEmpty();
    }

    @Override
    public boolean isDuplicateBusinessNumber(String businessNumber) {
        return publisherRepository.findByBusinessNumber(businessNumber).isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Publisher> publisher = publisherRepository.findByEmail(email);
        if(publisher.isPresent())
        {
            loginService.publisherLogin(publisher.get());
            log.info("@@@@@@@@@@@{ }",publisher.toString());
            return publisher.get();
        }
        throw new RuntimeException("등록되지 않는 회원.");
    }
}
