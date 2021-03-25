package com.team_project.shop.domain.product;

import com.team_project.shop.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {
    List<Products> findAllByUserId(Long id);
}
