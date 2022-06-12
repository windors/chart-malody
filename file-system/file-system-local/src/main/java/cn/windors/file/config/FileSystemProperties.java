package cn.windors.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Windor
 */
@ConfigurationProperties(prefix = "file")
@Data
public class FileSystemProperties {
    private String fileDir = "D:/tmp/malody-file";
}
