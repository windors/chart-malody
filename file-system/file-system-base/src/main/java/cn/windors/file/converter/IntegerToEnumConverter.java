package cn.windors.file.converter;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.google.common.collect.Maps;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;
import java.util.Objects;

/**
 * @author Windor
 */
public class IntegerToEnumConverter<T extends IEnum<?>> implements Converter<Integer, T> {

    private final Map<Integer, T> enumMap = Maps.newHashMap();

    public IntegerToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T t : enums) {
            enumMap.put(Integer.getInteger(t.getValue().toString()), t);
        }
    }

    @Override
    public T convert(Integer source) {
        T t = enumMap.get(source);
        if (Objects.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
