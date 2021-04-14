package com.team_project.shop.config.auth.dto;

import com.team_project.shop.domain.Publisher.Publisher;
import lombok.Getter;

@Getter
public class SessionPublisher {

    private Long id;
    private String email;
    private String businessName;
    private String businessNumber;

    public SessionPublisher(Publisher publisher) {
        this.id = publisher.getId();
        this.email = publisher.getEmail();
        this.businessName = publisher.getBusinessName();
        this.businessNumber = publisher.getBusinessNumber();
    }
}
