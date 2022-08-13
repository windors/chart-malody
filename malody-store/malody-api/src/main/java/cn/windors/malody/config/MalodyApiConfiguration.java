package cn.windors.malody.config;


import cn.windors.malody.handler.impl.FirstCanMalodyUploadJudgmentHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Windor
 */
@Configuration
@Slf4j
public class MalodyApiConfiguration {
    @Bean
    public FirstCanMalodyUploadJudgmentHandler judgmentHandler() {
        log.info("当前谱面上传拒绝策略：仅首次上传者可上传");
        return new FirstCanMalodyUploadJudgmentHandler();
    }
}
