package com.team_project.shop.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {
    @Transactional
    Products findByProductId(Long productId);
}
