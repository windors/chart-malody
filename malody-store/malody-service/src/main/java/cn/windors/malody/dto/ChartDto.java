package cn.windors.malody.dto;

import cn.windors.malody.entity.core.Chart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Windor
 */
@EnableConfigurationProperties
@ApiModel("返回给Malody的铺面数据信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartDto {
    @ApiModelProperty("铺面id")
    private Long cid;

    @ApiModelProperty("上传者用户id")
    private Long uid;

    @ApiModelProperty("作者用户名")
    private String creator;

    @ApiModelProperty("谱面难度名，比如4K Easy")
    private String version;

    @ApiModelProperty("谱面难度名，比如4K Easy")
    private Integer level;

    @ApiModelProperty("谱面游玩长度，单位：秒")
    private Integer length;

    @ApiModelProperty("谱面状态，2代表Stable，1代表Beta，0代表Alpha")
    private Integer type;

    @ApiModelProperty("谱面下载大小，单位：字节")
    private Integer size;

    @ApiModelProperty("模式谱面,定义参见**模式定义**")
    private Integer mode;
}
