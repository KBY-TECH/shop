package com.team_project.shop.domain.Response;

import lombok.Getter;

@Getter
public enum StatusCode {

    OK(200, "OK"),
    CREATED(201, "CREATED"),
    NO_CONTENT(204, "NO_CONTENT"),
    UNAUTOHRIZED(401, "UNAUTHORIZED"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

    int statusCode;
    String code;

    StatusCode(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
