package cn.windors.malody.type;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Windor
 */
public enum CoreFileTypeEnum implements IEnum<Integer> {
    /**
     *
     */
    CHART(0, "铺面文件"),
    SONG(1, "歌曲文件"),
    SKIN(2, "皮肤文件");
    private final Integer code;

    private CoreFileTypeEnum(Integer code, String msg) {
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }
}