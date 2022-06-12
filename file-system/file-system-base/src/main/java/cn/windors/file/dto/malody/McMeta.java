package cn.windors.file.dto.malody;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Windor
 */
@Data
@NoArgsConstructor
public class McMeta {
    private Integer id;
    private String creator;
    private String background;
    private String version;
    private Integer preview;
    private Integer mode;
    private Long time;
    private McSong song;

    private Object mode_ext;
}