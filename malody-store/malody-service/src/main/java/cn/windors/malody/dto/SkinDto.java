package cn.windors.malody.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Windor
 */
public class SkinDto {

    private Integer id;

    @ApiModelProperty("作者uid")
    private Integer uid;

    @ApiModelProperty("作者用户名")
    private String creator;

    @ApiModelProperty("皮肤名称")
    private String name;

    @ApiModelProperty("皮肤封面的url")
    private String cover;

    @ApiModelProperty("皮肤预览图的url，如果有多个预览图，使用|分割")
    private String preview;

    @ApiModelProperty("皮肤热度，含义可以自定义")
    private Integer hot;

    @ApiModelProperty("皮肤更新时间，unix time")
    private Integer time;

    @ApiModelProperty("模式谱面,定义参见**模式定义**")
    private Integer mode;
}
