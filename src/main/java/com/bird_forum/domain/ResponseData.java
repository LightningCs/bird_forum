package com.bird_forum.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "响应数据")
public class ResponseData<E> {
    @Schema(description = "状态码")
    String code;

    @Schema(description = "响应信息")
    String message;

    @Schema(description = "响应数据")
    E data;

    /**
     * 响应成功
     * @param <E> 响应数据类型
     * @return 响应数据
     */
    public static <E> ResponseData<E> success() {
        return init("200", "操作成功!", null);
    }

    /**
     * 响应成功
     * @param <E> 响应数据类型
     * @param data 响应数据
     * @return 响应数据
     */
    public static <E> ResponseData<E> success(E data) {
        return init("200", "操作成功!", data);
    }

    /**
     * 响应成功
     * @param <E> 响应数据类型
     * @param message 响应信息
     * @return 响应数据
     */
    public static <E> ResponseData<E> success(String message) {
        return init("200", message, null);
    }

    /**
     * 响应失败
     * @param <E> 响应数据类型
     * @return 响应数据
     */
    public static <E> ResponseData<E> error() {
        return init("500", "操作失败!", null);
    }

    /**
     * 响应失败
     * @param <E> 响应数据类型
     * @param data 响应数据
     * @return 响应数据
     */
    public static <E> ResponseData<E> error(E data) {
        return init("500", "操作失败!", data);
    }

    /**
     * 响应失败
     * @param <E> 响应数据类型
     * @param message 响应信息
     * @return 响应数据
     */
    public static <E> ResponseData<E> error(String message) {
        return init("500", message, null);
    }

    /**
     * 设置响应信息
     * @param message 响应信息
     * @return 响应数据
     */
    public <E> ResponseData<E> message(String message) {
        this.setMessage(message);
        return (ResponseData<E>) this;
    }

    /**
     * 响应数据初始化
     * @param <E> 响应数据类型
     * @param code 状态码
     * @param message 响应信息
     * @param data 响应数据
     * @return 响应数据
     */
    private static  <E> ResponseData<E> init(String code, String message, E data) {
        ResponseData<E> responseData = new ResponseData<>();
        responseData.setCode(code);
        responseData.setMessage(message);
        responseData.setData(data);
        return responseData;
    }
}
