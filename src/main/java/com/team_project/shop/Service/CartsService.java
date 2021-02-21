package com.team_project.shop.Service;

import com.team_project.shop.domain.cart.CartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartsService {
    private final CartsRepository cartsRepository;
}
