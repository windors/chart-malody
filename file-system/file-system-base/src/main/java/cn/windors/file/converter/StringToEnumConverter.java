package cn.windors.file.converter;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.google.common.collect.Maps;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;
import java.util.Objects;

/**
 * https://blog.csdn.net/weixin_41249041/article/details/96764460
 * @author Windor
 * 因为 Post 请求可以对传入的 json 属性定义类型，但是 Get 请求后台接收到的参数都是 String 类型，因此需要在创建一个 Converter 类
 */
public class StringToEnumConverter <T extends IEnum<?>> implements Converter<String, T> {


    private final Map<String, T> enumMap = Maps.newHashMap();

    public StringToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T t : enums) {
            enumMap.put(t.getValue().toString(), t);
        }
    }

    @Override
    public T convert(String source) {
        T t = enumMap.get(source);
        if (Objects.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
