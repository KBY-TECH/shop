package com.team_project.shop.web;

import com.team_project.shop.Service.OrderService;
import com.team_project.shop.Service.UsersService;
import com.team_project.shop.config.auth.LoginUser;
import com.team_project.shop.config.auth.dto.SessionUser;
import com.team_project.shop.domain.Response.BaseResponse;
import com.team_project.shop.domain.Response.CommonResponse;
import com.team_project.shop.domain.Response.ErrorResponse;
import com.team_project.shop.domain.Response.StatusCode;
import com.team_project.shop.domain.order.Orders;
import com.team_project.shop.network.request.OrdersSaveRequestDto;
import com.team_project.shop.network.request.OrdersUpdateRequestDto;
import com.team_project.shop.network.response.OrdersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/order")
@RestController
public class ApiOrderController {
    private final OrderService orderService;
    private final UsersService usersService;

    @PostMapping("")
    public ResponseEntity<? extends BaseResponse> save(@LoginUser SessionUser sessionUser, @RequestBody OrdersSaveRequestDto requestDto) {
        Long id = orderService.save(usersService.findById(sessionUser.getId()), requestDto);
        if(id == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(StatusCode.BAD_REQUEST.getStatusCode(), "요청이 올바르지 않습니다. 주문을 추가할 수 없습니다."));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<? extends BaseResponse> update(@LoginUser SessionUser sessionUser, @RequestBody OrdersUpdateRequestDto requestDto, @PathVariable Long orderId) {
        Optional<OrdersResponseDto> order = orderService.findById(orderId);
        if(!order.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(StatusCode.NOT_FOUND.getStatusCode(), "존재하지 않는 주문입니다."));
        if(!order.get().getUser().getId().equals(sessionUser.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(StatusCode.FORBIDDEN.getStatusCode(), "해당 주문에 접근할 수 없습니다."));
        orderService.update(orderId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<? extends BaseResponse> delete(@LoginUser SessionUser sessionUser, @PathVariable Long orderId) {
        Optional<OrdersResponseDto> order = orderService.findById(orderId);
        if(!order.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(StatusCode.NOT_FOUND.getStatusCode(), "존재하지 않는 주문입니다."));
        if(!order.get().getUser().getId().equals(sessionUser.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(StatusCode.FORBIDDEN.getStatusCode(), "해당 주문에 접근할 수 없습니다."));
        Long id = orderService.delete(orderId);
        if(id == 0)
            return ResponseEntity.ok().body(new CommonResponse(null, "주문을 취소할 수 없습니다."));
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/my")
    public ResponseEntity<? extends BaseResponse> findByUser(@LoginUser SessionUser sessionUser) {
        return ResponseEntity.ok().body(new CommonResponse(orderService.findByUserId(sessionUser.getId()),"주문목록 조회 성공"));
    }
}
