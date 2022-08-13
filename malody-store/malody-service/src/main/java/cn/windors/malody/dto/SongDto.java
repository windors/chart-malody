package cn.windors.malody.dto;

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
@ApiModel("返回给Malody的歌曲数据信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

    @ApiModelProperty("歌曲id")
    private Long sid;

    @ApiModelProperty("歌曲图标路径")
    private String cover;

    @ApiModelProperty("歌曲长度，单位秒")
    private Integer length;

    @ApiModelProperty("歌曲bpm")
    private Double bpm;

    @ApiModelProperty("歌曲标题")
    private String title;

    @ApiModelProperty("歌曲作曲家")
    private String artist;

    @ApiModelProperty("歌曲所包含的谱面类型bitmask值，例如歌曲同时包含key和catch两个模式的谱面，bitmask值为(1 << 0) | (1 << 3) = 9")
    private Integer mode;

    @ApiModelProperty("歌曲最后更新时间")
    private Long time;
}