package cn.windors.malody.response;

import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.entity.core.Song;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Windor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityChartResponse {
    @ApiModelProperty("song id")
    private Long sid;

    @ApiModelProperty("chart id")
    private Long cid;

    @ApiModelProperty("作者uid")
    private Long uid;

    @ApiModelProperty("作者用户名")
    private String creator;

    @ApiModelProperty("歌曲标题")
    private String title;

    @ApiModelProperty("作者uid")
    private String artist;

    @ApiModelProperty("谱面难度名，比如4K Easy")
    private String version;

    @ApiModelProperty("谱面难度值，数字")
    private Integer level;

    @ApiModelProperty("谱面游玩长度，单位：秒")
    private Integer length;

    @ApiModelProperty("谱面状态，2代表Stable，1代表Beta，0代表Alpha")
    private Integer type;

    @ApiModelProperty("歌曲图标路径")
    private String cover;

    // TODO ???
    @ApiModelProperty("歌曲最后更新时间???, 如果是LocalDatetime则Event返回时会报错...")
    private Long time = 0L;

    @ApiModelProperty("模式谱面,定义参见**模式定义**")
    private Integer mode;

    public static ActivityChartResponse getInstance(Chart chart, Song song) {
        return new ActivityChartResponse(chart.getSid(), chart.getId(), chart.getUid(), chart.getCreator(), song.getTitle(),
                song.getArtist(), chart.getVersion(), chart.getLevel(), chart.getLength(), chart.getExamineState().getValue(),
                song.getCoverUri(), 0L, chart.getMode().getValue());
    }
}
