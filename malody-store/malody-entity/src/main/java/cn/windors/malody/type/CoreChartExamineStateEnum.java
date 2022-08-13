package cn.windors.malody.type;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Windor
 * 谱面审核状态
 */
public enum CoreChartExamineStateEnum implements IEnum<Integer> {
    /**
     * 2代表Stable，1代表Beta，0代表Alpha
     */
    ALPHA(0, "Alpha"),
    BETA(1, "Beta"),
    STABLE(2, "Stable");
    private final Integer value;

    CoreChartExamineStateEnum(Integer value, String msg) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static CoreChartExamineStateEnum getTypeByValue(int value) {
        switch(value) {
            case 0 : return ALPHA;
            case 1 : return BETA;
            case 2 : return STABLE;
            default: return null;
        }
    }
}