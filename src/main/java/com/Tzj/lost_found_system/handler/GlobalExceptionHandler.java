package com.Tzj.lost_found_system.handler;

import com.Tzj.lost_found_system.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * 统一捕获所有未处理的异常，返回友好的JSON响应，避免泄露系统堆栈信息
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有未预期的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.error("服务器内部错误，请联系管理员");
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result handleNullPointerException(NullPointerException e) {
        log.error("空指针异常: ", e);
        return Result.error("数据异常，请检查输入");
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("非法参数: {}", e.getMessage());
        return Result.error("参数错误：" + e.getMessage());
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result handleRuntimeException(RuntimeException e) {
        log.error("运行时异常: ", e);
        return Result.error("操作失败：" + e.getMessage());
    }
}
