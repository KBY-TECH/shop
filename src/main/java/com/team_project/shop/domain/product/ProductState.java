package com.team_project.shop.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Getter
@RequiredArgsConstructor
public enum ProductState {
    ONSALE,
    SUSPENSION,
    OUTOFSTOCK;


}