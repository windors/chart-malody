package cn.windors.malody.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Windor
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadDto {
    @ApiModelProperty("文件名")
    private String name;
    @ApiModelProperty("文件md5值")
    private String hash;
    @ApiModelProperty("文件的下载地址url")
    private String file;
}
