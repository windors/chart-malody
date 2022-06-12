package cn.windors.file.dto.malody;

import lombok.Data;

import java.util.List;

/**
 * @author Windor
 */
@Data
public class ZipSongDto {
    private String name;
    private List<ZipChartDto> charts;

    @Data
    public static class ZipChartDto{
        private Long cid;
        private Long mainFid;
        private List<Long> fids;
    }
}
