package cn.windors.malody.type;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Windor
 * TODO 分析有几种解析状态
 */
public enum CoreResolveStateEnum implements IEnum<Integer>  {
    AUTO_UPDATE_ERROR,
    WAIT_UPDATE,
    AUTO_UPDATED;

    @Override
    public Integer getValue() {
        return null;
    }
}
