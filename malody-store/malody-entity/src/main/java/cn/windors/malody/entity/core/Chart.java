package cn.windors.malody.entity.core;

import cn.windors.malody.entity.BaseEntity;
import cn.windors.malody.type.CoreChartExamineStateEnum;
import cn.windors.malody.type.CoreChartModeEnum;
import cn.windors.malody.type.CoreResolveStateEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * (Chart)实体类
 *
 * @author windor
 * @since 2022-03-16 21:35:30
 */
@Data
@Accessors(chain = true)
@TableName("core_chart")
public class Chart extends BaseEntity {
    @TableId
    @ApiModelProperty("铺面id")
    private Long id;

    @ApiModelProperty("歌曲id")
    private Long sid;

    @ApiModelProperty("Malody上传者id")
    private Long uid;

//    注释原因： 暂时还未发现除前端显示外的其他作用
//    TODO API部分完成后再处理该字段
//    @ApiModelProperty("原始标题（主文件）")
//    private String name;

    @ApiModelProperty("作者用户名")
    private String creator;

    @ApiModelProperty("谱面难度名")
    private String version;

    @ApiModelProperty(value = "铺面难度值", notes = "必填, 否则将搜索不到")
    private Integer level;

    @ApiModelProperty(value = "谱面游玩长度", notes = "单位：秒")
    private Integer length;

    @ApiModelProperty(value = "谱面下载大小", notes = "单位：字节")
    private Integer size;

    @ApiModelProperty(value = "背景图片", notes = "存储URI")
    private String backgroundUri;

    @ApiModelProperty(value = "歌曲", notes = "存储URI")
    private String songUri;

    @ApiModelProperty(value = "铺面下载地址", notes = "保存的是相对地址, 使用时需要手动拼前缀")
    private String mainUri;

    @ApiModelProperty("铺面主文件md5")
    private String md5;

    @ApiModelProperty("谱面的模式")
    private CoreChartModeEnum mode;

    @ApiModelProperty("审核状态,用来给管理员更新数据用")
    @TableField(fill = FieldFill.INSERT)
    private CoreResolveStateEnum resolveState;

    @ApiModelProperty("解析状态")
    @TableField(fill = FieldFill.INSERT)
    private CoreChartExamineStateEnum examineState;
}

