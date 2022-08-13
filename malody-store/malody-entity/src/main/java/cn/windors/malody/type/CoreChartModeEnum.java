package cn.windors.malody.type;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Windor
 */
public enum CoreChartModeEnum implements IEnum<Integer> {
    /**
     * NULL* 是为了配合Mybatis Plus中的枚举映射，否则会有问题
     */
    KEY(0, "KEY"),
    NULL1(1, ""),
    NULL2(2, ""),
    CATCH(3, "CATCH"),
    PAD(4, "PAD"),
    TAIKO(5, "TAIKO"),
    RING(6, "RING"),
    SLIDE(7, "SLIDE"),
    LIVE(8, "LIVE");
    private final Integer value;
    private final String msg;

    CoreChartModeEnum(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static CoreChartModeEnum getModeByValue(int code) {
        switch (code) {
            case 0:
                return KEY;
            case 3:
                return CATCH;
            case 4:
                return PAD;
            case 5:
                return TAIKO;
            case 6:
                return RING;
            case 7:
                return SLIDE;
            case 8:
                return LIVE;
            default:
                return null;

        }
    }
}
