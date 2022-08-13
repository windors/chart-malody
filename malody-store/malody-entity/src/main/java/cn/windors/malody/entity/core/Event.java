package cn.windors.malody.entity.core;

import cn.windors.malody.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Windor
 */
@Data
@TableName("core_event")
public class Event extends BaseEntity {
    private static final long serialVersionUID = 587525579023975621L;

    @ApiModelProperty("事件id")
    private Long id;

    @ApiModelProperty("事件标题")
    private String name;

    @ApiModelProperty("事件介绍")
    private String introduce;

    @ApiModelProperty("事件展示的封面")
    private String cover;

    @ApiModelProperty("发起者")
    private String sponsor;

    @ApiModelProperty("开始时间")
    private LocalDateTime start;

    @ApiModelProperty("结束时间")
    private LocalDateTime end;

    @TableField(exist = false)
    private List<EventRecord> records;
}