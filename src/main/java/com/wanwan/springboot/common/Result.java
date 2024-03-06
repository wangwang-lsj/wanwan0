package com.wanwan.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：玩玩
 * @since：2024/1/23 21:35
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;
    public static Result success(){
        return new Result(Constants.CODE_200,"",null    );
    }
    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }
    public static Result error(String code, String msg){
        return new Result(code,msg,null);
    }
    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }
}
