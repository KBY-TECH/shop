package com.team_project.shop.Service;

import com.team_project.shop.Service.IFS.PublisherService_IFS;
import com.team_project.shop.network.request.PublisherCreateRequestDto;
import com.team_project.shop.network.request.PublisherUpdatePasswordRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.team_project.shop.network.response.HttpStatusResponseEntity.RESPONSE_OK;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PublisherServiceTest {

    @Autowired
    PublisherService_IFS publisherService;

    @BeforeEach
    void demoDate() {
        List<PublisherCreateRequestDto> dtos = demoData();
        dtos.forEach(publisherCreateRequestDto -> {
            publisherService.signUp(publisherCreateRequestDto);
        });
    }

    @Test
    @DisplayName("상품 판매자 회원가입")
    void signUp() {
        PublisherCreateRequestDto requestDto = PublisherCreateRequestDto.of("qwer@naver.com", "kkk", "K 상사", "5555544444", "naver.com123!");
        ResponseEntity<HttpStatus> http = publisherService.signUp(requestDto);
        Assertions.assertThat(http).isEqualTo(RESPONSE_OK);
    }

    @Test
    @DisplayName("상품 판매자 비밀번호 변경 update")
    void updatePassword() {

    }

    @Test
    @DisplayName("상품 판매자 회원가입 중 이메일 중복 체크")
    void isDuplicateEmail() {
        boolean duplicateEmail = publisherService.isDuplicateEmail("korea@naver.com");
        boolean duplicateEmail2 = publisherService.isDuplicateEmail("usa@naver.com");
        boolean duplicateEmail3 = publisherService.isDuplicateEmail("japan@naver.com");
        Assertions.assertThat(duplicateEmail).isEqualTo(false);
        Assertions.assertThat(duplicateEmail2).isEqualTo(false);
        Assertions.assertThat(duplicateEmail3).isEqualTo(false);
    }

    @Test
    @DisplayName("상품 판매자 회원가입 중 사업자 번호 중복 체크")
    void isDuplicateBusinessNumber() {
        boolean duplicateBusinessNumber = publisherService.isDuplicateBusinessNumber("1234567891");
        boolean duplicateBusinessNumber2 = publisherService.isDuplicateBusinessNumber("2345678911");
        boolean duplicateBusinessNumber3 = publisherService.isDuplicateBusinessNumber("3456789112");
        Assertions.assertThat(duplicateBusinessNumber).isEqualTo(true);
        Assertions.assertThat(duplicateBusinessNumber2).isEqualTo(true);
        Assertions.assertThat(duplicateBusinessNumber3).isEqualTo(true);
    }


    public List<PublisherCreateRequestDto> demoData() {
        List<PublisherCreateRequestDto> dtos = new ArrayList<>();
        PublisherCreateRequestDto of = PublisherCreateRequestDto.of("korea@naver.com", "kim", "korea 상사", "1234567891", "naver.com123!");
        PublisherCreateRequestDto of2 = PublisherCreateRequestDto.of("usa@naver.com", "john", "usa 상사", "2345678911", "naver.com123!");
        PublisherCreateRequestDto of3 = PublisherCreateRequestDto.of("japan@naver.com", "kaka", "japan 상사", "3456789112", "naver.com123!");
        dtos.add(of);
        dtos.add(of2);
        dtos.add(of3);

        return dtos;
    }
}