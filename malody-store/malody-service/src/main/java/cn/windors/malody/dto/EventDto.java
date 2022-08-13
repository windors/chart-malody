package cn.windors.malody.dto;


import cn.windors.malody.entity.core.Event;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Windor
 */
@Data
@AllArgsConstructor
@ApiModel("活动")
public class EventDto {
    @ApiModelProperty("event id")
    private Long eid;

    @ApiModelProperty("活动标题")
    private String name;

    @ApiModelProperty("活动发起人，歌单作者等")
    private String sponsor;

    @ApiModelProperty("活动开始时间，格式为yyyy-mm-dd")
    private LocalDateTime start;

    @ApiModelProperty("活动结束时间，格式为yyyy-mm-dd")
    private LocalDateTime end;

    @ApiModelProperty("活动展示的封面")
    private String cover;

    @ApiModelProperty("活动是否有效")
    private Boolean active;
}
