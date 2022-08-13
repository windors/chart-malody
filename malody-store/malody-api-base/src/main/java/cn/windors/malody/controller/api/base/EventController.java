package cn.windors.malody.controller.api.base;

import cn.windors.malody.dto.ActivityChartDto;
import cn.windors.malody.dto.EventDto;
import cn.windors.malody.entity.core.Event;
import cn.windors.malody.properties.MalodyCoreProperties;
import cn.windors.malody.response.MalodyDataResponse;
import cn.windors.malody.response.MalodyListResponse;
import cn.windors.malody.service.EventService;
import cn.windors.malody.util.DtoStaticFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wind_Yuan
 */
@RestController
@RequestMapping("api/store")
public class EventController {

    @Resource
    private EventService eventService;

    @GetMapping("/events")
    @ApiOperation(value = "分区列表（202103添加）", notes = "获取所有活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "返回当前有效的活动", allowEmptyValue = true, name = "active", paramType = "int"),
            @ApiImplicitParam(value = "翻页起点", allowEmptyValue = true, name = "from", paramType = "int")
    })
    public MalodyDataResponse<EventDto> events(
            @RequestParam(defaultValue = "1") Integer active,
            @RequestParam(defaultValue = "1") Integer from) {
        Page<Event> page = eventService.queryEvent(active, from, MalodyCoreProperties.pageSize);
        final List<EventDto> result = new ArrayList<>(page.getRecords().size());
        page.getRecords().forEach(event -> {
            result.add(DtoStaticFactory.getEventDtoInstance(event));
        });
        return MalodyDataResponse.success(result);
    }

    /**
     * from参数有些奇怪
     * @param eid
     * @param org
     * @param from
     * @return
     */
    @GetMapping("/event")
    @ApiOperation(value = "活动谱面列表（202103添加）", notes = "获取指定活动下所有谱面")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "返回当前有效的活动", name = "eid", paramType = "int"),
            @ApiImplicitParam(value = "是否返回原始标题", allowEmptyValue = true, name = "org", paramType = "int", defaultValue = "0"),
            @ApiImplicitParam(value = "翻页起点", allowEmptyValue = true, name = "from", paramType = "int", defaultValue = "1")
    })
    public MalodyListResponse<ActivityChartDto> event(
            @RequestParam Long eid,
            @RequestParam(defaultValue = "0") Integer org,
            @RequestParam(defaultValue = "1") Integer from) {
        List<ActivityChartDto> result = eventService.selectActivityChartDto(eid, org);
        return MalodyListResponse.noPage(result);
    }
}
