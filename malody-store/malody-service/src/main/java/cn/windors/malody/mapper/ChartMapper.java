package cn.windors.malody.mapper;

import cn.windors.malody.entity.core.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Windor
 */
public interface ChartMapper extends BaseMapper<Chart> {
    /**
     * 通过歌曲id获取谱面
     * @param sid 歌曲id
     * @return
     */
    List<Chart> getChartBySid(Long sid);
}
