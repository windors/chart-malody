package cn.windors.file;

import cn.windors.file.config.FileSystemProperties;
import cn.windors.file.handler.FileMetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Windor
 */
@SpringBootApplication
@EnableConfigurationProperties(FileSystemProperties.class)
@MapperScan("cn.windors.file.mapper")
public class FileSystemLocalApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileSystemLocalApplication.class, args);
    }

    @Bean
    public MetaObjectHandler tableFieldFillHandler() {
        return new FileMetaObjectHandler();
    }
}
