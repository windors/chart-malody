package cn.windors.file.config;

import io.swagger.annotations.SwaggerDefinition;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Wind_Yuan
 */
@Configuration
@EnableSwagger2
@SwaggerDefinition(basePath = "/")
@Data
public class SwaggerConfiguration {

    private String title = "Malody V File Server";

    private String author = "windors";

    private String url = "http://www.windors.cn";

    private String email = "windor@vip.qq.com";

    private String version = "0.1";

    private String description = "本地存储实现";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("cn.windors.file.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .contact(new Contact(author, url, email))
                .version(version)
                .description(description)
                .build();
    }
}
