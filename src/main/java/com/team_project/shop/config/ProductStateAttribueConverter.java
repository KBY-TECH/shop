package com.team_project.shop.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ProductStateAttribueConverter implements AttributeConverter<String, Integer> {
    @Override
    public String convertToEntityAttribute(Integer code) {
        if(code == 1){
            return "ONSALE";
        }else if(code==2){
            return "SUSPENSION";
        }else if(code==3){
            return "OUTOFSTOCK";
        }
        return "ERROR";
    }

    @Override
    public Integer convertToDatabaseColumn(String state) {
        if("ONSALE".equals(state)){
            return 1;
        }else if("SUSPENSION".equals(state)){
            return 2;
        }else if("OUTOFSTOCK".equals(state)){
            return 3;
        }
        return 0;
    }
}
