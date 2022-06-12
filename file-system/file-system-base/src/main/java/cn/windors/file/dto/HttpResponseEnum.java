package cn.windors.file.dto;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Windor
 */
public enum HttpResponseEnum implements IEnum<Integer> {
    /**
     *
     */
    OK(200, "OK"),
    ERROR(500, "error");

    @EnumValue
    @JsonValue
    private final int code;

    private final String msg;

    HttpResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
