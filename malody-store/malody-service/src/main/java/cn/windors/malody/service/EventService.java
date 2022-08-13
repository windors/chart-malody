package cn.windors.malody.service;

import cn.windors.malody.dto.ActivityChartDto;
import cn.windors.malody.entity.core.Event;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author Windor
 */
public interface EventService {
    Page<Event> queryEvent(Integer active, Integer from, int pageSize);

    List<ActivityChartDto> selectActivityChartDto(Long eid, Integer org);
}
