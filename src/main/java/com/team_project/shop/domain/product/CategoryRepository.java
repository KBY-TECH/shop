package com.team_project.shop.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Transactional
    Category findByName(String name);
    boolean existsByName(String name);
    List<Category> findAllByParent(Category category);
    boolean existsByParent(Category category);
}
