package cn.windors.malody.service;

import cn.windors.malody.entity.core.Chart;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Wind_Yuan
 * TODO 实现
 */
public interface ChartService extends IService<Chart> {
    Page<Chart> queryChart(int sid, int beta, int mode, int from, int pageSize, int promote);
}
