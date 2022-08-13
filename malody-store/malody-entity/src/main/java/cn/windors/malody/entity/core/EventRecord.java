package cn.windors.malody.entity.core;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Windor
 */
@Data
@TableName("core_chart__event")
@Accessors(chain = true)
public class EventRecord {
    private Long id;

    private Long cid;

    private Long eid;

    private Integer weight;

    @TableField(exist = false)
    private Chart chart;
}