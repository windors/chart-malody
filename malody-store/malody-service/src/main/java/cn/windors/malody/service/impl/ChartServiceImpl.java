package cn.windors.malody.service.impl;

import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.mapper.ChartMapper;
import cn.windors.malody.service.ChartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Windor
 */
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart> implements ChartService {
    @Override
    public Page<Chart> queryChart(int sid, int beta, int mode, int from, int pageSize, int promote) {
        QueryWrapper<Chart> queryWrapper = new QueryWrapper<Chart>().eq("sid", sid);

//        if(beta == 0) {
//            log.warn("Malody客户端已更新，请及时处理beta: {}。API中beta指是否返回非stable谱面，本系统暂时设计为beta不是默认值会搜索stable谱面", beta);
//            queryWrapper.eq("type", ChartTypeEnum.STABLE);
//        }
        if (mode != -1) {
            queryWrapper.eq("mode", mode);
        }
        return page(new Page<>(from, pageSize),
                queryWrapper);
    }
}
