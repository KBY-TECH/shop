package com.team_project.shop.web;

import com.team_project.shop.Service.CartService;
import com.team_project.shop.Service.UsersService;
import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.domain.Response.BaseResponse;
import com.team_project.shop.domain.Response.CommonResponse;
import com.team_project.shop.domain.Response.ErrorResponse;
import com.team_project.shop.domain.Response.StatusCode;
import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.network.request.CartsSaveRequestDto;
import com.team_project.shop.network.request.CartsUpdateRequestDto;
import com.team_project.shop.network.response.CartsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/cart")
@RestController
public class ApiCartController {
    private final CartService cartService;
    private final UsersService usersService;

    @PostMapping("")
    public ResponseEntity<? extends BaseResponse> save(@LoginUser SessionUser sessionUser, @RequestBody CartsSaveRequestDto requestDto) {
        Long id = cartService.save(usersService.findById(sessionUser.getId()), requestDto);
        if(id == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(StatusCode.BAD_REQUEST.getStatusCode(), "요청이 올바르지 않습니다. 장바구니를 추가할 수 없습니다."));
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<? extends BaseResponse> update(@LoginUser SessionUser sessionUser, @RequestBody CartsUpdateRequestDto requestDto, @PathVariable Long cartId) {
        Optional<CartsResponseDto> cart = cartService.findById(cartId);
        if(!cart.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(StatusCode.NOT_FOUND.getStatusCode(), "존재하지 않는 장바구니입니다."));
        if(!cart.get().getUser().getId().equals(sessionUser.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(StatusCode.FORBIDDEN.getStatusCode(), "잘못된 요청입니다. 해당 장바구니에 접근할 수 없습니다."));
        cartService.update(cartId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<? extends BaseResponse> delete(@LoginUser SessionUser sessionUser, @PathVariable Long cartId) {
        Optional<CartsResponseDto> cart = cartService.findById(cartId);
        if(!cart.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(StatusCode.NOT_FOUND.getStatusCode(), "존재하지 않는 장바구니입니다."));
        if(!cart.get().getUser().getId().equals(sessionUser.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(StatusCode.FORBIDDEN.getStatusCode(), "잘못된 요청입니다. 해당 장바구니에 접근할 수 없습니다."));
        cartService.delete(cartId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/my")
    public ResponseEntity<? extends BaseResponse> findByUser(@LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok().body(new CommonResponse(cartService.findByUserId(sessionUser.getId()), "장바구니 목록 조회 성공"));
    }

}
