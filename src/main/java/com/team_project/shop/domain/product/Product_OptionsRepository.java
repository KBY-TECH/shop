package com.team_project.shop.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Product_OptionsRepository extends JpaRepository<Product_Options, Long> {
    Product_Options findByProductId(Long productId);
    List<Product_Options> findAllByProductId(Long productId);
}
