package com.team_project.shop.domain.Publisher;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Getter
@Entity
public class Publisher extends BaseEntity {


    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String BusinessNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password;


    @Builder
    public Publisher(String email, String businessNumber, Role role, String password) {
        this.email = email;
        BusinessNumber = businessNumber;
        this.role = role;
        this.password = password;
    }
}
