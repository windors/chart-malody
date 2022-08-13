package cn.windors.malody.service.impl;

import cn.windors.malody.dto.ActivityChartDto;
import cn.windors.malody.entity.core.Event;
import cn.windors.malody.mapper.EventMapper;
import cn.windors.malody.service.EventService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Windor
 */
@Service
public class EventServiceImpl implements EventService {
    @Resource
    private EventMapper eventMapper;

    @Override
    public Page<Event> queryEvent(Integer active, Integer from, int pageSize) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (active == 1) {
            queryWrapper.le("start", now).ge("end", now);
        } else {
            queryWrapper.lt("end", now).or(eventQueryWrapper -> eventQueryWrapper.gt("start", now));
        }
        return eventMapper.selectPage(new Page<Event>(from, pageSize), queryWrapper);
    }

    @Override
    public List<ActivityChartDto> selectActivityChartDto(Long eid, Integer org) {
        return eventMapper.selectActivityChartDto(eid, org);
    }
}
