package cn.windors.malody.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author Wind_Yuan
 */
@Data
public class MalodyChart {
    private McMeta meta;

    @JsonIgnore
    private Object time;

    @JsonIgnore
    private Object note;

    @Data
    public static class McMeta {
        private Integer id;
        private String creator;
        private String background;
        private String version;
        private Integer preview;
        private Integer mode;
        private Long time;
        private McSong song;

        @JsonIgnore
        private Object mode_ext;

        @Data
        public static class McSong {
            private Integer id;
            private String title;
            private String artist;
            private String titleorg;
            private String artistorg;
            private String file;
            private Double bpm;
        }
    }
}
