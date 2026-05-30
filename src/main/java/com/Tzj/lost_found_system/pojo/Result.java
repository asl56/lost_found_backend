package com.Tzj.lost_found_system.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object data;
    private Object data2;

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 返回成功的统一响应（修复拼写：sucesss -> success）
    public static Result success(){
        return new Result(1,"success",null);
    }

    // 返回带数据的成功响应
    public static Result success(Object data){
        return new Result(1,"success",data);
    }

    // 返回失败响应，携带错误信息
    public static Result error(String msg){
        return new Result(0,msg,null);
    }

    // 返回带两个数据对象的成功响应（用于同时返回JWT令牌和用户信息等场景）
    public static Result successAndObject(Object data, Object data2) {
        return new Result(1,"success",data,data2);
    }
}
