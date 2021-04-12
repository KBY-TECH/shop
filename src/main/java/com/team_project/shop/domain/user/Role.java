package com.team_project.shop.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUSET("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    PUBLISHER("ROLE_PUBLISHER","판매자"),
    ADMIN("ROLE_ADMIN","관리자");


    private final String key;
    private final String title;
}
