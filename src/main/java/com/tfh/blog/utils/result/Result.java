package com.tfh.blog.utils.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/18 18:56
 * TODO: 结果集
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private int code;
    private String msg;
    private Object data;


    /**
     * 构建基础的 Result
     *
     * @param resultType 枚举类型
     * @param data       数据
     * @return Result
     */
    private static Result build(ResultType resultType, Object data) {
        return new Result(resultType.getCode(), resultType.getMsg(), data);
    }

    /**
     * 返回成功的结果集，并且携带data数据
     *
     * @param data 数据
     * @return Result
     */
    public static Result ok(Object data) {
        return build(ResultType.SUCCESS, data);
    }

    /**
     * 直接返回成功的结果集，不需要任何形参
     *
     * @return Result
     */
    public static Result ok() {
        return ok(null);
    }

    /**
     * 返回错误的结果集的基本方法
     *
     * @param resultType 枚举类型
     * @param data       数据
     * @return Result
     */
    public static Result error(ResultType resultType, Object data) {
        return build(resultType, data);
    }

    /**
     * 返回错误的结果集，携带data数据
     *
     * @param data 数据
     * @return Result
     */
    public static Result error(Object data) {
        return error(ResultType.ERROR, data);
    }

    /**
     * 直接返回错误结果集，无任何形参
     *
     * @return Result
     */
    public static Result error() {
        return error(null);
    }

}
