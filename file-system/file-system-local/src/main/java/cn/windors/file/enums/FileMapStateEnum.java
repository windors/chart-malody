package cn.windors.file.enums;

import cn.windors.file.entity.FileMap;
import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Windor
 */
public enum FileMapStateEnum implements IEnum<Integer> {
    /**
     * 上传出错
     */
    ERROR(-1),
    /**
     * 待上传
     */
    WATING(0),
    /**
     * md5记录的是正常的文件
     */
    NORMAL(1),
    /**
     * 此文件已被压缩，md5记录的是原始的文件
     */
    ZIP(2);

    private final int value;

    FileMapStateEnum(int value) {
        this.value = value;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }
}
