package com.team_project.shop.web;

import com.team_project.shop.Service.OrderService;
import com.team_project.shop.Service.UserService_tmp;
import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.network.request.OrdersSaveRequestDto;
import com.team_project.shop.network.request.OrdersUpdateRequestDto;
import com.team_project.shop.network.response.OrdersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/order")
@RestController
public class ApiOrderController {
    private final OrderService orderService;
    private final UserService_tmp userServiceTmp;

    @PostMapping("")
    public Long save(@LoginUser SessionUser sessionUser, @RequestBody OrdersSaveRequestDto requestDto) {
        return orderService.save(userServiceTmp.findById(sessionUser.getId()), requestDto);
    }

    @PutMapping("/{orderId}")
    public Long update(@LoginUser SessionUser sessionUser, @RequestBody OrdersUpdateRequestDto requestDto, @PathVariable Long orderId) {
        if(!orderService.findById(orderId).equals(sessionUser.getId()))
            return -1l;
        return orderService.update(orderId, requestDto);
    }

    @DeleteMapping("/{orderId}")
    public Long delete(@LoginUser SessionUser sessionUser, @PathVariable Long orderId) {
        //이거 서비스에서 수정하기!!! 배송완료 같은거면 취소불가하는 로직 짜서 리턴값 달리 주기
        return orderService.delete(orderId);
    }


    @GetMapping("/my")
    public List<OrdersResponseDto> findByUserId(@LoginUser SessionUser sessionUser) {
        return orderService.findByUserId(sessionUser.getId());
    }
}
