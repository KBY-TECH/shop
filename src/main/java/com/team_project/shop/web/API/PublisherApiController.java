package com.team_project.shop.web.API;

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
    @PostMapping("")
    public ResponseEntity<HttpStatus> publisherCreate(@Valid @RequestBody PublisherCreateRequestDto requestDto)
    {
        return null;
    }

    @PostMapping("/businessNumberCheck")
    public ResponseEntity<HttpStatus> businessNumberCheck(@RequestParam(value = "business_number") @Valid @NotEmpty String businessNumber)
    {
        return null;
    }

}
