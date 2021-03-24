package com.team_project.shop.web;

import com.team_project.shop.Service.CartService;
import com.team_project.shop.Service.UserService_tmp;
import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.network.request.CartsSaveRequestDto;
import com.team_project.shop.network.request.CartsUpdateRequestDto;
import com.team_project.shop.network.response.CartsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/carts")
@RestController
public class ApiCartController {
    private final CartService cartService;
    private final UserService_tmp userServiceTmp;

    @PostMapping("")
    public Long save(@LoginUser SessionUser sessionUser, @RequestBody CartsSaveRequestDto requestDto) {
        return cartService.save(userServiceTmp.findById(sessionUser.getId()), requestDto);
    }

    @PutMapping("/{cartId}")
    public Long update(@LoginUser SessionUser sessionUser, @RequestBody CartsUpdateRequestDto requestDto, @PathVariable Long cartId) {
        if(!cartService.findById(cartId).getUser().getId().equals(sessionUser.getId()))
            return -1l;
        return cartService.update(cartId, requestDto);
    }

    @DeleteMapping("/{cartId}")
    public Long delete(@LoginUser SessionUser sessionUser, @PathVariable Long cartId) {
        if(!cartService.findById(cartId).getUser().getId().equals(sessionUser.getId()))
            return -1l;
        return cartService.delete(cartId);
    }


    @GetMapping("/my")
    public List<CartsResponseDto> findByUserId(@LoginUser SessionUser sessionUser) {
        return cartService.findByUserId(sessionUser.getId());
    }

}
