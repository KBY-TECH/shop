package com.team_project.shop.web.API;

import com.team_project.shop.Service.IFS.UsersService_IFS;
import com.team_project.shop.network.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/api/user")
@RestController
public class UsersApiController {
    private final UsersService_IFS usersService;

    @PostMapping("")
    public ResponseEntity<HttpStatus> userCreate(@Valid @RequestBody UserRequestDto requestDto)
    {
        return usersService.singUp(requestDto);
    }

    @PostMapping("/mailCheck")
    public ResponseEntity duplicateEmailChecking(@RequestParam(value = "email",required = true) @Validated @NotEmpty @Email String email)
    {
//        log.info("request Parameter : {}",email);
        if(usersService.isDuplicateEmail(email))
            return ResponseEntity.ok("사용가능한 이메일 입니다.");
        return new ResponseEntity("중복된 이메일 입니다.", HttpStatus.CONFLICT);
    }


}
