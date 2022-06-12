package cn.windors.file.dto.malody;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Windor
 */
@Data
@NoArgsConstructor
public class McSong {
    private Integer id;
    private String title;
    private String artist;
    private String titleorg;
    private String artistorg;
    private String file;
    private Double bpm;
}