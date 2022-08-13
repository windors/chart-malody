package cn.windors.malody.entity.core;

import cn.windors.malody.entity.BaseEntity;

import cn.windors.malody.type.CoreResolveStateEnum;
import cn.windors.malody.type.CoreSongExamineStateEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * (Song)实体类
 * @author windor
 * @since 2022-03-16 21:44:34
 */
@Data
@TableName("core_song")
public class Song extends BaseEntity {

    @TableId
    @ApiModelProperty("歌曲id")
    private Long id;

    @ApiModelProperty("歌曲标题")
    private String title;

    @ApiModelProperty("原始标题")
    private String originTitle;

    @ApiModelProperty(value = "歌曲图标", notes = "存储Uri")
    private String coverUri;

    @ApiModelProperty("歌曲bpm")
    private Double bpm;

    @ApiModelProperty("歌曲作曲家")
    private String artist;

    @ApiModelProperty("歌曲所包含的谱面类型bitmask值，例如歌曲同时包含key和catch两个模式的谱面，bitmask值为(1 << 0) | (1 << 3) = 9")
    private Integer mode;

    @ApiModelProperty("歌曲长度，单位秒")
    private Integer length;

    @ApiModelProperty("解析状态")
    private CoreResolveStateEnum resolveState;

    @ApiModelProperty("审核状态,用来给管理员更新数据用")
    private CoreSongExamineStateEnum examineState;

    private String songUri;

    @TableField(exist = false)
    private List<Chart> charts;
}

