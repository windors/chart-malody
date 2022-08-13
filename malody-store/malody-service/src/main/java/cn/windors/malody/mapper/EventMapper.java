package cn.windors.malody.mapper;

import cn.windors.malody.dto.ActivityChartDto;
import cn.windors.malody.entity.core.Event;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Wind_Yuan
 */
public interface EventMapper extends BaseMapper<Event> {
    List<ActivityChartDto> selectActivityChartDto(Long eid, Integer org);
}
