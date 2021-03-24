package com.team_project.shop.domain.cart;

import com.team_project.shop.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartsRepository extends JpaRepository<Carts, Long> {
    List<Carts> findByUser(Users user);
    List<Carts> findByUserName(String userName);
    List<Carts> findByUserId(Long userId);
}

