package cn.windors.malody.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Windor
 */
@Getter
@Setter
public class SkinUrlDto {
    @ApiModelProperty("皮肤文件名")
    private String name;
    @ApiModelProperty("皮肤下载地址url")
    private String url;
    @ApiModelProperty("皮肤id")
    private Integer id;
}
