package cn.windors.file.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Windor
 */
public enum UploadModeEnum implements IEnum<Integer> {
    /**
     * 正常模式，直接上传
     */
    NORMAL(0),
    /**
     * 已被压缩
     */
    ZIP(1);

    private final int value;
    UploadModeEnum(int value) {
        this.value = value;
    }
    @Override
    public Integer getValue() {
        return value;
    }
}
