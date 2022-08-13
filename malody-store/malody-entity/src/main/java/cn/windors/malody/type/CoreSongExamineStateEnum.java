package cn.windors.malody.type;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Windor
 * 歌曲审核状态
 */
public enum CoreSongExamineStateEnum implements IEnum<Integer> {
    /**
     * 2代表Stable，1代表Beta，0代表Alpha
     */
    UNKNOWN(0, "未知（待处理）"),
    BETA(1, "有版权");
    private final Integer value;

    CoreSongExamineStateEnum(Integer value, String msg) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}