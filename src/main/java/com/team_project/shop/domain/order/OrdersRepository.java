package com.team_project.shop.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserName(String userName);
    List<Orders> findByUserId(Long id);
}

