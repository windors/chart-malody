package cn.windors.malody.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Windor
 */
public enum MalodyResponseEnum implements IEnum<Integer> {
    /**
     *
     */
    NOT_EXISTS_SKIN(-2, "sid对应皮肤不存在"),
    OK(0, "OK"),
    LENGTH_NOT_EQUAL(1, "长度不相等"),
    NOT_EXISTS(2, "不存在"),
    FILE_MISSING(404, "文件缺失"),
    /**
     * 客户端重入hash中未包含主hash，但是客户端没有传入
     */
    FILE_MD5_NOT_IN_HASH(410, "未传入主hash");

    @EnumValue
    @JsonValue
    private final int code;

    private final String msg;

    MalodyResponseEnum(int code, String msg) {
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
