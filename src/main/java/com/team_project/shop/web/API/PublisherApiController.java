package com.team_project.shop.web.API;

import com.team_project.shop.Service.IFS.Publisher_IFS;
import com.team_project.shop.network.request.PublisherCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/api/publisher")
@RestController
public class PublisherApiController {
    private final Publisher_IFS publisher;
    @PostMapping("")
    public ResponseEntity<HttpStatus> publisherCreate(@Valid @RequestBody PublisherCreateRequestDto requestDto)
    {
        return publisher.signUp(requestDto);
    }

    @PostMapping("/emailCheck")
    public ResponseEntity emailCheck(@RequestParam(value ="email") @Valid @Email @NotEmpty String email)
    {
        if(publisher.isDuplicateEmail(email))
        {
            return ResponseEntity.ok("사용가능한 이메일 입니다.");
        }
        return new ResponseEntity("중복된 이메일 입니다.",HttpStatus.CONFLICT);
    }
    @PostMapping("/businessNumberCheck")
    public ResponseEntity businessNumberCheck(@RequestParam(value = "business_number") @Valid @NotEmpty String businessNumber)
    {
        if(publisher.isDuplicateBusinessNumber(businessNumber))
        {
            return ResponseEntity.ok("사용가능한 사업자 번호 입니다.");
        }
        return new ResponseEntity("중복된 사업자 번호 입니다.",HttpStatus.CONFLICT);
    }


}
