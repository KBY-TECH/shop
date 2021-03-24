package com.team_project.shop.Service;

import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.domain.cart.CartsRepository;
import com.team_project.shop.domain.coupon.CouponRepository;
import com.team_project.shop.domain.middleEntity.OrderDetails;
import com.team_project.shop.domain.middleEntity.OrderDetailsRepository;
import com.team_project.shop.domain.order.Orders;
import com.team_project.shop.domain.order.OrdersRepository;
import com.team_project.shop.domain.product.Product_OptionsRepository;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.network.request.OrdersSaveRequestDto;
import com.team_project.shop.network.request.OrdersUpdateRequestDto;
import com.team_project.shop.network.response.OrderDetailsResponseDto;
import com.team_project.shop.network.response.OrdersResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final CouponRepository couponRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartsRepository cartsRepository;

    @Transactional
    public Long save(Users user, OrdersSaveRequestDto requestDto){
        Orders order = ordersRepository.save(requestDto.toEntity(user));
        if(requestDto.getCouponId() != 0) {  //쿠폰이 있는 경우는 order에 추가해줌
            order.addCoupon(couponRepository.findById(requestDto.getCouponId()).get());
        }
        //유저가 쿠폰을 가지고 있는지 확인하는 함수 필요할까? -> ajax로 쿠폰 선택(클릭 이벤트)마다 쿠폰가능한지 확인해주고, url에 노출되지 않아서 확인 필요 없을 듯
        for(Long cartId : requestDto.getCartId()){
            Carts cart = cartsRepository.findById(cartId).get();
            orderDetailsRepository.save(OrderDetails.builder()
                    .order(order)
                    .productOption(cart.getProductOption())
                    .quantity(cart.getQuantity())
                    .build());
        }
        return order.getId();
    }

    @Transactional
    public Long update(Long orderId, OrdersUpdateRequestDto requestDto){
        Optional<Orders> order = ordersRepository.findById(orderId);
        if(!order.isPresent())
            return 0l;
        order.get().update(requestDto.getOrderStatus());
        return orderId;
    }

    @Transactional
    public Long delete(Long orderId){
        Optional<Orders> order = ordersRepository.findById(orderId);
        if(!order.isPresent())
            return 0l;
        ordersRepository.deleteById(orderId);
        return orderId;
    }

    @Transactional(readOnly = true)
    public OrdersResponseDto findById(Long orderId){
        Optional<Orders> order = ordersRepository.findById(orderId);
        if(!order.isPresent())
            return null;    //예외처리방법 알아보기 optional리턴하고 컨트롤러에서 처리할 방향으로 생각 중
        return OrdersResponseDto.builder().orders(order.get()).build();

    }

    @Transactional(readOnly = true)
    public List<OrdersResponseDto> findByUserId(Long userId) {
        List<Orders> ordersList = ordersRepository.findByUserId(userId);
        return ordersList.stream().map(OrdersResponseDto::new).collect(Collectors.toList());
    }
}
