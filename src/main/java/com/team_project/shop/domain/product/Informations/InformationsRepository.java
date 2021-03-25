package com.team_project.shop.domain.product.Informations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationsRepository <T extends Informations> extends JpaRepository<T, Long> {
}
