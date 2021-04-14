package com.team_project.shop.domain.Publisher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {

    Optional<Publisher> findByEmail(String email);

    Optional<Publisher> findByBusinessNumber(String businessNumber);

}
