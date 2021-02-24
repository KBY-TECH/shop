package com.team_project.shop.Service;

import com.team_project.shop.domain.cart.Carts;
import com.team_project.shop.domain.cart.CartsRepository;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.product.Product_OptionsRepository;
import com.team_project.shop.domain.user.Users;
import com.team_project.shop.network.request.CartsSaveRequestDto;
import com.team_project.shop.network.request.CartsUpdateRequestDto;
import com.team_project.shop.network.response.CartsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartsService {
    private final CartsRepository cartsRepository;
    private final Product_OptionsRepository productOptionsRepository;

    /*
        잘못된 product option id가 들어오면 0리턴
        그 외에는 저장된 carts id값 리턴
     */
    @Transactional
    public Long save(Users user, Long productOptionId, CartsSaveRequestDto requestDto){
        Optional<Product_Options> productOption = productOptionsRepository.findById(productOptionId);
        if(!productOption.isPresent())
            return 0l;
        return cartsRepository.save(requestDto.toEntity(user, productOption.get())).getId();
    }

    /*
    잘못된 cart option id가 들어오면 0리턴
    그 외에는 수정된 carts id값 리턴
 */
    @Transactional
    public Long update(Long cartId, CartsUpdateRequestDto requestDto){
        Optional<Carts> cart = cartsRepository.findById(cartId);
        if(!cart.isPresent())
            return 0l;
        cart.get().update(requestDto.getQuantity());
        return cartId;
    }

    /*
        잘못된 cart option id가 들어오면 0리턴
        그 외에는 삭제된 carts id값 리턴
     */
    @Transactional
    public Long delete(Long cartId){
        Optional<Carts> cart = cartsRepository.findById(cartId);
        if(!cart.isPresent())
            return 0l;
        cartsRepository.deleteById(cartId);
        return cartId;
    }

    /*
        잘못된 cart option id가 들어오면 0리턴
        그 외에는 조회된 carts id값 리턴
     */
    @Transactional(readOnly = true)
    public CartsResponseDto findById(Long cartId){
        Optional<Carts> cart = cartsRepository.findById(cartId);
        if(!cart.isPresent())
            return null; //예외처리 방법에 대해 알아보기
        return CartsResponseDto.builder()
                .carts(cart.get())
                .build();
    }
}
