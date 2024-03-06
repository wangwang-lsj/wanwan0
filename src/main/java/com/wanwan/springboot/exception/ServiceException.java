package com.wanwan.springboot.exception;

import lombok.Getter;

/**
 * @author：玩玩
 * @since：2024/1/23 22:25
 * @description:
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;
    public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }

}
