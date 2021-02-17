package com.team_project.shop.config;

public class ShopException extends Exception{
    private final int ERR_CODE;
    public ShopException(String msg, int err_code){
        super(msg);
        ERR_CODE = err_code;
    }
    public ShopException(String msg){
        this(msg,100);
    }
    public int getErrCode(){
        return ERR_CODE;
    }
}
